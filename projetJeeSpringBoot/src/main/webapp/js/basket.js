function seeProduct(productId) {
    var form = document.createElement('form');
    form.method = 'post';
    form.action = './Product';

    var productIdInput = document.createElement('input');
    productIdInput.type = 'hidden';
    productIdInput.name = 'productId';
    productIdInput.value = productId;

    form.appendChild(productIdInput);

    document.body.appendChild(form);
    form.submit();
}

function seeMarket(sellerId) {
    var form = document.createElement('form');
    form.method = 'post';
    form.action = './Market';

    var sellerIdInput = document.createElement('input');
    sellerIdInput.type = 'hidden';
    sellerIdInput.name = 'sellerId';
    sellerIdInput.value = sellerId;

    form.appendChild(sellerIdInput);

    document.body.appendChild(form);
    form.submit();
}