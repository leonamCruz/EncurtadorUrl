function salvar() {
    const urlObtida = document.getElementById('entrada').value

    if (!urlObtida || !/^https?:\/\/.+\..+/i.test(urlObtida)) {
        alert("Por favor, insira uma URL válida.")
        return
    }

    const data = { url: urlObtida }
    const dominioAtual = window.location.origin
    const api = `${dominioAtual}/gerar`

    fetch(api, {
        method: 'POST',
        headers: { 'Content-Type': 'application/json' },
        body: JSON.stringify(data)
    })
        .then(response => response.json())
        .then(data => {
            const urlEncurtada = `${dominioAtual}/${data.urlEncurtada}`
            document.getElementById('urlEncurtada').innerText = urlEncurtada
            const qrcodeElement = document.getElementById('qrcode')
            QRCode.toCanvas(qrcodeElement, urlEncurtada, function (error) {
                if (error) {
                    console.error(error)
                }
            })
            const modal = new bootstrap.Modal(document.getElementById('modal'))
            modal.show()
        })
        .catch(error => {
            alert(error)
            console.log(error)
        })
}

function copiar() {
    const urlEncurtada = document.getElementById('ondevaiaurl').innerText
    navigator.clipboard.writeText(urlEncurtada)
        .then(() => {
            alert('URL copiada para a área de transferência!')
        })
        .catch(err => {
            alert('Erro ao copiar a URL')
            console.log(err)
        });
}
