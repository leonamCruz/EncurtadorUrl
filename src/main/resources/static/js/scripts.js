function salvar() {
    const input = document.getElementById('entrada');
    const urlObtida = input.value.trim();

    if (!urlObtida || !/^https?:\/\/.+\..+/i.test(urlObtida)) {
        alert("Por favor, insira uma URL válida.");
        return;
    }

    const dominioAtual = window.location.origin;
    const api = dominioAtual + '/gerar';

    fetch(api, {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json'
        },
        body: JSON.stringify({ url: urlObtida })
    })
        .then(function (response) {
            if (!response.ok) {
                // controller retorna 400 sem body
                throw new Error("Erro ao Gerar a URL");
            }
            return response.json();
        })
        .then(function (data) {
            const urlEncurtada = dominioAtual + '/' + data.urlEncurtada;

            const output = document.getElementById('urlEncurtada');
            output.innerText = urlEncurtada;

            const canvas = document.getElementById('qrcode');
            QRCode.toCanvas(canvas, urlEncurtada);

            new bootstrap.Modal(document.getElementById('modal')).show();
        })
        .catch(function (error) {
            console.error(error);
            alert(error.message);
        });
}

function copiar() {
    const output = document.getElementById('urlEncurtada');
    const texto = output.innerText.trim();

    if (!texto) {
        alert('Nenhuma URL para copiar');
        return;
    }

    navigator.clipboard.writeText(texto)
        .then(function () {
            alert('URL copiada!');
        })
        .catch(function (err) {
            console.error(err);
            alert('Erro ao copiar a URL');
        });
}