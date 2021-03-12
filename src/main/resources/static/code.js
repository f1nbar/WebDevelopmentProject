function productForm() {
            document.getElementById("create-product").innerHTML = "<div class=\"title\" id=\"create-product\">\n" +
                "<script src=\"/code.js\">\n" +
                "    </script>\n" +
                "<div class=\"formContainer\">\n" +
                "<form id='form1'>\n" +
                "  <div class=\"form-group\">\n" +
                "    <label for=\"name\">Product Name</label>\n" +
                "    <input type=\"text\" class=\"form-control\" id=\"name\" placeholder=\"Yummy Ice Cream\">\n" +
                "  </div>\n" +
                "  <div class=\"form-group\">\n" +
                "    <label for=\"price\">Price</label>\n" +
                "    <input type=\"number\" class=\"form-control\" id=\"price\" step=\".01\" placeholder=\"€69.99\">\n" +
                "  </div>\n" +
                "  <div class=\"form-check\">\n" +
                "  <input class=\"form-check-input\" type=\"checkbox\" value=\"\" id=\"visible\">\n" +
                "  <label class=\"form-check-label\" for=\"defaultCheck1\">\n" +
                "    Visible\n" +
                "  </label>\n" +
                "</div>\n" +
                " </form>\n" +
                " <form id='form2'>\n" +
                "  <div class=\"form-group\">\n" +
                "    <label for=\"exampleFormControlFile1\">Product Image</label>\n" +
                "    <input type=\"file\" class=\"form-control-file\" id=\"image\" name=\"image\" accept=\"image/png, image/jpeg\" >\n" +
                "  </div>\n"+
                "    <button type=\"submit\" class=\"btn btn-primary\" onclick=\"submitForms()\">Create Product</button>\n" +
                "</form>\n" +
                "</div>"

}

async function createProduct() {
    let inputName = document.getElementById("name").value;
    let inputPrice = document.getElementById("price").value;
    let inputVisible = false;
    if (document.getElementById("visible").checked) {
        let inputVisible = true;
    }
    const file = $('#image').get(0).files[0];
    const newFileName = inputName + "PNG";
    const formData = new FormData();
    formData.append('file', file, newFileName);

    let add = new Product(inputName, inputPrice, inputVisible);
    const url = "products/add";
    const xhr = new XMLHttpRequest();
    xhr.open("POST", url, true);
    xhr.setRequestHeader('Content-Type', 'application/json');
    xhr.send(JSON.stringify(add));

    const url2 = "products/image";

    const request = new Request(url2, {
        method: 'POST',
        body: formData,
    });

    fetch(request)
        .then(res => res.json())
        .then(res => console.log(res));
}

function Product(productName, price, isVisible) {
    this.productName = productName;
    this.price = price;
    this.isVisible = isVisible;
}

submitForms = function(){
    document.getElementById("form1").submit();
    document.getElementById("form2").submit();
}

