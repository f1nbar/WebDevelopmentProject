function addToCart(productId, productName, price) {
    // Get cart info from local storage
    var cart = localStorage.getItem("cart")
    // Check if cart has been initialised yet
    if (cart == null)
        cart = {}
    else
        cart = JSON.parse(cart)

    // Check if given item already exists in cart
    if (cart[productId] == null) {
        // If not, add it
        cart[productId] = {
            id: productId,
            productName: productName,
            price: price,
            quantity: 1
        }
        var cartTable = document.getElementById("cartTable")
        var row = cartTable.insertRow(cartTable.rows.length-1)
        row.id = "item_" + productId
        row.insertCell(0).innerHTML = productName
        row.insertCell(1).innerHTML = price
        row.insertCell(2).innerHTML = 1
        row.insertCell(3).innerHTML = "<Button type=\"button\" onclick=\"removeFromCart('" + productId + "')\">Remove</Button>"
    } else {
        // If so, increment the items quantity value
        cart[productId] = {
            id: productId,
            productName: productName,
            price: price,
            quantity: cart[productId].quantity + 1
        }
        var cartTable = document.getElementById("cart")
        var row = document.getElementById("item_" + productId)
        row.cells[2].innerHTML = cart[productId].quantity
    }
    // Save updated cart info to local storage
    localStorage.setItem("cart", JSON.stringify(cart))
    console.log('Added ' + productId + ' to the cart!')
}

function removeFromCart(productId) {
    // Get cart info from local storage
    var cart = JSON.parse(localStorage.getItem("cart"));
    delete cart[productId];
    localStorage.setItem("cart", JSON.stringify(cart));
    // Save updated cart info to local storage
    document.getElementById("item_" + productId).remove();
    console.log("Deleted: " + productId)
}

// Runs when the page loads, initialises the cart tab with cart item information
function initCart() {
    // Get cart info from local storage
    var cart = JSON.parse(localStorage.getItem("cart"))
    var cartTable = document.getElementById("cartTable")
    for (const productId in cart) {
        var productInfo = cart[productId]
        var row = cartTable.insertRow(cartTable.rows.length-1)
        row.id = "item_" + productId
        row.insertCell(0).innerHTML = productInfo.productName
        row.insertCell(1).innerHTML = productInfo.price
        row.insertCell(2).innerHTML = productInfo.quantity
        row.insertCell(3).innerHTML = "<Button type=\"button\" onclick=\"removeFromCart('" + productId + "')\">Remove</Button>"
    }
}

function initOrder() {
    // Get cart info from local storage
    var cart = JSON.parse(localStorage.getItem("cart"))
    var orderTable = document.getElementById("orderTable")
    var total=0
    for (const productId in cart) {
        var productInfo = cart[productId]
        var row = orderTable.insertRow(orderTable.rows.length-1)
        row.id = "item_" + productId
        row.insertCell(0).innerHTML = "<img class='orderImage' src='images/" + productId + ".png'>"
        row.insertCell(1).innerHTML = productInfo.productName
        row.insertCell(2).innerHTML = productInfo.price
        row.insertCell(3).innerHTML = productInfo.quantity
        row.insertCell(4).innerHTML = (productInfo.price*productInfo.quantity)
        total += (productInfo.price*productInfo.quantity)
    }
    document.getElementById("totalVal").innerHTML = total.toFixed(2)
}

function pay() {
    if (document.getElementById("AL1").value == ""
        || document.getElementById("AL2").value == ""
        || document.getElementById("town").value == ""
        || document.getElementById("county").value == ""
    ) {
        document.getElementById("addressError").innerHTML = "Missing fields"
    } else {
        document.getElementById("paymentError").innerHTML = ""
    }
    if (document.getElementById("cardNo").value == ""
            || document.getElementById("month").value == ""
            || document.getElementById("year").value == ""
            || document.getElementById("cvc").value == ""
        ) {
            document.getElementById("paymentError").innerHTML = "Missing fields"
        } else {
            document.getElementById("paymentError").innerHTML = ""
        }

    if (document.getElementById("AL1").value == ""
            || document.getElementById("AL2").value == ""
            || document.getElementById("town").value == ""
            || document.getElementById("county").value == ""
        ) {
            document.getElementById("addressError").innerHTML = "Missing fields"
        } else {
            document.getElementById("addressError").innerHTML = ""
        }
    // Get address from page
    var address = ""
    address += document.getElementById("AL1").value + ",\n"
    address += document.getElementById("AL2").value + ",\n"
    address += document.getElementById("town").value + ",\n"
    address += document.getElementById("county").value
    // Get items list
    var cart = JSON.parse(localStorage.getItem("cart"))
    var items = [address]
    for (const productId in cart) {
        quantity = cart[productId].quantity
        items.push(productId + "_" + quantity)
    }
    console.log(items)
    //Get user details
    var userRequest = new XMLHttpRequest()
    var user = {}
    userRequest.onreadystatechange = () => {
        if (userRequest.readyState != 4) return;
        user = JSON.parse(userRequest.responseText)
        console.log(user)
        var checkoutRequest = new XMLHttpRequest()
        checkoutRequest.onreadystatechange = () => {
            if (checkoutRequest.readyState != 4) return;
            // TODO: Show Pop Up
            console.log(checkoutRequest.responseText)
        }
        checkoutRequest.open('POST', "/checkout/?customerId=" + user.id)
        checkoutRequest.setRequestHeader('Content-type', 'application/json')
        checkoutRequest.send(items)
    }
    userRequest.open('GET', "/user/details")
    userRequest.send()
}




function toggle(id) {
    var state = document.getElementById(id).style.display;
    if (state == 'block') {
        document.getElementById(id).style.display = 'none';
    } else {
        document.getElementById(id).style.display = 'block';
    }
}
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
        // "  <div class=\"form-group\">\n" +
        // "    <label for=\"exampleFormControlFile1\">Product Image</label>\n" +
        // "    <input type=\"file\" class=\"form-control-file\" id=\"image\" name=\"image\" accept=\"image/png, image/jpeg\" >\n" +
        // "  </div>\n"+
        "    <button type=\"submit\" class=\"btn\" onclick=\"createProduct()\">Create Product</button>\n" +
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
    // const file = $('#image').get(0).files[0];
    // const newFileName = inputName + "PNG";
    // const formData = new FormData();
    // formData.append('file', file, newFileName);

    let add = new Product(inputName, inputPrice, inputVisible);
    const url = "products/add";
    const xhr = new XMLHttpRequest();
    xhr.open("POST", url, false);
    xhr.setRequestHeader('Content-Type', 'application/json');
    xhr.send(JSON.stringify(add));

   // const url2 = "products/image";
    //
    // const request = new Request(url2, {
    //     method: 'POST',
    //     body: formData,
    // });
    //
    // fetch(request)
    //     .then(res => res.json())
    //     .then(res => console.log(res));
}

function editProduct(id) {
    console.log(id)
    let inputName = document.getElementById("name").value;
    let inputPrice = document.getElementById("price").value;
    let inputVisible = false;
    if (document.getElementById("visible").checked) {
        let inputVisible = true;
    }
    let add = new Product(id, inputName, inputPrice, inputVisible);
    const url = "/products/edit";
    const xhr = new XMLHttpRequest();
    xhr.open("POST", url, false);
    xhr.setRequestHeader('Content-Type', 'application/json');
    xhr.send(JSON.stringify(add));

}


function Product(productName, price, isVisible) {
    this.productName = productName;
    this.price = price;
    this.isVisible = isVisible;
}

function Product(id, productName, price, isVisible){
    this.id = id;
    this.productName = productName;
    this.price = price;
    this.isVisible = isVisible;

}
submitForms = function(){
    document.getElementById("form1").submit();
  //  document.getElementById("form2").submit();
}

