function openImage() {
    let url = "./website/images/schuetzenheim.jpg";
    var image = new Image();
    image.src = url;
    //image.style.visibility = "hidden";    // Maybe you can remove this
    image.style.position = "absolute";
    image.style.left = "-9999px";
    var htmlImageElement = document.body.appendChild(image);
    document.body.appendChild(image);
    window.open(image.src, "_blank", "height=" + image.height + " width=" + image.width);
    window.resizeTo(image.width, image.height);
    console.log(image.width +" "+ image.height);
}