function productForm() {
            document.getElementById("create-product").innerHTML = "<div class=\"title\" id=\"create-product\">\n" +
                "<script src=\"/code.js\">\n" +
                "    </script>\n" +
                "<div class=\"formContainer\">\n" +
                "<form>\n" +
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
                "  <div class=\"form-group\">\n" +
                "    <label for=\"exampleFormControlFile1\">Product Image</label>\n" +
                "    <input type=\"file\" class=\"form-control-file\" id=\"exampleFormControlFile1\">\n" +
                "  </div>\n" +
                "    <button type=\"submit\" class=\"btn\" onclick=\"createProduct()\">Create Product</button>\n" +
                "</form>\n" +
                "</div>"

}

function createProduct() {
    let inputName = document.getElementById("name").value;
    let inputPrice = document.getElementById("price").value;
    let inputVisible = false;
    if (document.getElementById("visible").checked){
        let inputVisible = true;
    }
    let add = new Product(inputName, inputPrice, inputVisible);
    const url = "products/add";
    const xhr = new XMLHttpRequest();
    xhr.open("POST", url, false);
    xhr.setRequestHeader('Content-Type', 'application/json');
    console.log(xhr.send(JSON.stringify(add)));
    xhr.send(JSON.stringify(add));
}

function Product(productName, price, isVisible) {
    this.productName = productName;
    this.price = price;
    this.isVisible = isVisible;
}