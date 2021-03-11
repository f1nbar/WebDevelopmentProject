function productForm() {
            document.getElementById("create-product").innerHTML = "<div class=\"title\" id=\"create-product\">\n" +
                "<script src=\"/code.js\">\n" +
                "    </script>\n" +
                "<div class=\"formContainer\">\n" +
                "<form>\n" +
                "  <div class=\"form-group\">\n" +
                "    <label for=\"name\">Product Name</label>\n" +
                "    <input type=\"text\" class=\"form-control\" id=\"form1\" placeholder=\"Yummy Ice Cream\">\n" +
                "  </div>\n" +
                "  <div class=\"form-group\">\n" +
                "    <label for=\"price\">Price</label>\n" +
                "    <input type=\"number\" class=\"form-control\" id=\"form1\" placeholder=\"€69.99\">\n" +
                "  </div>\n" +
                "  <div class=\"form-check\">\n" +
                "  <input class=\"form-check-input\" type=\"checkbox\" value=\"\" id=\"defaultCheck1\">\n" +
                "  <label class=\"form-check-label\" for=\"defaultCheck1\">\n" +
                "    Visible\n" +
                "  </label>\n" +
                "</div>\n" +
                "  <div class=\"form-group\">\n" +
                "    <label for=\"exampleFormControlFile1\">Product Image</label>\n" +
                "    <input type=\"file\" class=\"form-control-file\" id=\"exampleFormControlFile1\">\n" +
                "  </div>\n" +
                "    <button type=\"submit\" class=\"btn btn-primary mb-2\" onclick=\"createProduct()\">Create Product</button>\n" +
                "</form>\n" +
                "</div>"

}

function createProduct() {
    let inputName = document.getElementById("name").value;
    let inputPrice = document.getElementById("price").value;
    let inputVisible = document.getElementById("form-check").value;
}

function Product(productName, price, isVisible) {
    this.productName = productName;
    this.price = price;
    this.isVisible = isVisible;
}