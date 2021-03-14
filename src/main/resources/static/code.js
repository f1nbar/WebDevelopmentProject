function addToCart(productId, productName, price) {
    // Get cart info from local storage
    var cart = localStorage.getItem("cart")
    var total = parseInt(localStorage.getItem("total"))
    var productInfo = {}
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
        productInfo = cart[productId]
        console.log(productInfo)
        var orderTable = document.getElementById("orderTable")
        var row = orderTable.insertRow(orderTable.rows.length-1)
        row.id = "item_" + productId
        row.insertCell(0).innerHTML = "<img class='orderImage' src='images/" + productId + ".png'>"
        row.insertCell(1).innerHTML = productInfo.productName
        row.insertCell(2).innerHTML = productInfo.price
        var button = document.createElement("button")
        button.innerHTML = "-"
        button.setAttribute("onclick", "decItem('" + productId + "')")
        row.insertCell(3).appendChild(button)
        row.insertCell(4).innerHTML = productInfo.quantity
        var button = document.createElement("button")
        button.innerHTML = "+"
        button.setAttribute("onclick", "incItem('" + productId + "')")
        row.insertCell(5).appendChild(button)
        row.insertCell(6).innerHTML = (productInfo.price*productInfo.quantity).toFixed(2)
        var button = document.createElement("button")
        button.innerHTML = "Remove"
        button.setAttribute("onclick", "removeFromCart('" + productId + "')")
        row.insertCell(7).appendChild(button)
    } else {
        productInfo = cart[productId]
        incItem(productId)
    }
    // Save updated cart info to local storage
    total = parseInt(total + price)
    localStorage.setItem("total", total)
    document.getElementById("totalVal").innerHTML = total.toFixed(2)
    localStorage.setItem("cart", JSON.stringify(cart))
    console.log('Added ' + productId + ' to the cart!')
}

function removeFromCart(productId) {
    // Get cart & total info from local storage
    var cart = JSON.parse(localStorage.getItem("cart"))
    var total = parseInt(localStorage.getItem("total"))
    // Remove item and decrement total
    total = total - parseInt(cart[productId].price*cart[productId].quantity)
    delete cart[productId]
    // Save cart and total
    localStorage.setItem("cart", JSON.stringify(cart))
    localStorage.setItem("total", total)
    // Update screen
    document.getElementById("item_" + productId).remove()
    document.getElementById("totalVal").innerHTML = total.toFixed(2)
}

$('.dropdown-menu').click(function(e) {
    e.stopPropagation();
});


function incItem(productId) {
    // Get
    var cart = JSON.parse(localStorage.getItem("cart"));
    var total = parseInt(localStorage.getItem("total"))
    // Edit
    cart[productId].quantity = cart[productId].quantity + 1
    total = total + parseInt(cart[productId].price)
    // Save
    localStorage.setItem("cart", JSON.stringify(cart))
    localStorage.setItem("total", total)
    // Render
    totalProduct = parseInt(document.getElementById("item_" + productId).cells[6].innerHTML) + parseInt(cart[productId].price)
    document.getElementById("item_" + productId).cells[6].innerHTML = totalProduct.toFixed(2)
    document.getElementById("item_" + productId).cells[4].innerHTML = cart[productId].quantity
    document.getElementById("totalVal").innerHTML = parseInt(total).toFixed(2)
}

function decItem(productId) {
    // Get
    var cart = JSON.parse(localStorage.getItem("cart"));
    var total = parseInt(localStorage.getItem("total"))
    // Edit
    if (cart[productId].quantity == 1) {
        removeFromCart(productId)
    } else {
        cart[productId].quantity = cart[productId].quantity - 1
        total = total - parseInt(cart[productId].price)
        // Save
        localStorage.setItem("cart", JSON.stringify(cart))
        localStorage.setItem("total", total)
        // Render
        totalProduct = parseInt(document.getElementById("item_" + productId).cells[6].innerHTML) - parseInt(cart[productId].price)
        document.getElementById("item_" + productId).cells[6].innerHTML = totalProduct.toFixed(2)
        document.getElementById("item_" + productId).cells[4].innerHTML = cart[productId].quantity
        document.getElementById("totalVal").innerHTML = parseInt(total).toFixed(2)
    }
}

// Runs when the page loads, initialises the cart tab with cart item information
//function initCart() {
//    // Get cart info from local storage
//    var cart = JSON.parse(localStorage.getItem("cart"))
//    var orderTable = document.getElementById("orderTable")
//    for (const productId in cart) {
//        var productInfo = cart[productId]
//        var row = orderTable.insertRow(orderTable.rows.length-1)
//        row.id = "item_" + productId
//        row.insertCell(0).innerHTML = productInfo.productName
//        row.insertCell(1).innerHTML = productInfo.price
//        row.insertCell(2).innerHTML = productInfo.quantity
//        row.insertCell(3).innerHTML = "<Button type=\"button\" onclick=\"removeFromCart('" + productId + "')\">Remove</Button>"
//    }
//}

function initOrder() {
    // Get cart info from local storage
    var cart = JSON.parse(localStorage.getItem("cart"))
    total = 0
    var orderTable = document.getElementById("orderTable")
    for (const productId in cart) {
        var productInfo = cart[productId]
        var row = orderTable.insertRow(orderTable.rows.length-1)
        row.id = "item_" + productId
        row.insertCell(0).innerHTML = "<img class='orderImage' src='images/" + productId + ".png'>"
        row.insertCell(1).innerHTML = productInfo.productName
        row.insertCell(2).innerHTML = productInfo.price
        var button = document.createElement("button")
        button.innerHTML = "-"
        button.setAttribute("onclick", "decItem('" + productId + "')")
        row.insertCell(3).appendChild(button)
        row.insertCell(4).innerHTML = productInfo.quantity
        var button = document.createElement("button")
        button.innerHTML = "+"
        button.setAttribute("onclick", "incItem('" + productId + "')")
        row.insertCell(5).appendChild(button)
        row.insertCell(6).innerHTML = (productInfo.price*productInfo.quantity).toFixed(2)
        var button = document.createElement("button")
        button.innerHTML = "Remove"
        button.setAttribute("onclick", "removeFromCart('" + productId + "')")
        row.insertCell(7).appendChild(button)
        total += (productInfo.price*productInfo.quantity)
    }
    localStorage.setItem("total", total)
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
    address += document.getElementById("AL1").value + ","
    address += document.getElementById("AL2").value + ","
    address += document.getElementById("town").value + ","
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
        console.log(userRequest.responseText)
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
        checkoutRequest.send(JSON.stringify(items))
    }
    userRequest.open('GET', "/user/details")
    userRequest.send()
}

function confirmOrder(id) {
    var req = new XMLHttpRequest()
    req.onreadystatechange = () => {
        if (req.readyState != 4) return;
        window.location.reload(true);
    }
    req.open('GET', "/orders/setConfirmed/?orderId=" + id)
    req.send()
}

function deliverOrder(id) {
    var req = new XMLHttpRequest()
    req.onreadystatechange = () => {
        if (req.readyState != 4) return;
        window.location.reload(true);
    }
    req.open('GET', "/orders/setDelivered/?orderId=" + id)
    req.send()
}

function cancelOrder(id) {
    var req = new XMLHttpRequest()
    req.onreadystatechange = () => {
        if (req.readyState != 4) return;
        window.location.reload(true);
    }
    req.open('GET', "/orders/setCancelled/?orderId=" + id)
    req.send()
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
        " <script src=\"https://cdnjs.cloudflare.com/ajax/libs/1000hz-bootstrap-validator/0.11.9/validator.min.js\"></script>\n" +
        "<div class=\"formContainer\">\n" +
        "<form  >\n" +
        "  <div class=\"form-group\">\n" +
        "    <label for=\"name\">Product Name</label>\n" +
        "    <input type=\"text\"  required data-error=\"You have a name dont you?\" class=\"form-control\" id=\"name\" placeholder=\"Yummy Ice Cream\">\n" +
        "  </div>\n" +
        "  <div class=\"form-group\">\n" +
        "    <label for=\"price\">Price</label>\n" +
        "    <input type=\"number\" required data-error=\"Name your price\" class=\"form-control\" id=\"price\" step=\".01\" placeholder=\"€69.99\">\n" +
        "  </div>\n" +
        "  <div class=\"form-check\">\n" +
        "  <input class=\"form-check-input\" type=\"checkbox\" value=\"\" id=\"visible\">\n" +
        "  <label class=\"form-check-label\" for=\"defaultCheck1\">\n" +
        "    Visible\n" +
        "  </label>\n" +
        "  <div class=\"form-group\">\n" +
        "    <label for=\"description\">Product Description</label>\n" +
        "    <input type=\"text\" required data-error=\"Make an effort!\" class=\"form-control\" id=\"description\" placeholder=\"Convincing argument to make" +
        " customer believe they need the product, or just lorem ipsum :)\">\n" +
        "  </div>\n" +
        "</div>\n" +
        "  <div class=\"form-group\">\n" +
        "    <label for=\"exampleFormControlFile1\">Product Image</label>\n" +
        "    <input type=\"file\" class=\"form-control-file\" id=\"image\" name=\"image\" accept=\"image/png, image/jpeg\" >\n" +
        "<label for=\"imgInp\" class=\"custom-file-upload\">Add Product Picture </label>" +
        "<input id=\"imgInp\"  type=\"file\" name=\"image\" accept=\"image/png, image/jpeg\">" +

        "  </div>\n"+
        "    <button class=\"btn btn-admin\" onclick=\"createProduct()\">Create Product</button>\n" +
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

    var add = {

    productName:inputName,
    price:inputPrice,
    isVisible: inputVisible

    }

    // const formData = new FormData();
    // formData.append('file', file, newFileName);

    // let add = new Product(inputName, inputPrice, inputVisible,file);

    const url = "products/add";
    const xhr = new XMLHttpRequest();
    xhr.open("POST", url, false);
    xhr.setRequestHeader('Content-Type', 'application/json');
    xhr.send(JSON.stringify(add));


    var savedProduct = JSON.parse(xhr.response)

    var file = $('#imgInp').get(0).files[0];
    var newFileName = savedProduct.id + ".png";
    var formData = new FormData();
    formData.append('image', file, newFileName);

   const url2 = "products/image";

    const request = new Request(url2, {
        method: 'POST',
        body: formData,
    });

    fetch(request)
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

submitForms = function(){
    document.getElementById("form1").submit();
    document.getElementById("form2").submit();
}

