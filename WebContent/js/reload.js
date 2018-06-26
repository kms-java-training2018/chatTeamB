// フレームをreloadする方法
function doReloadTheFrame() {

    // フレームのDOM要素を取得
    var iframe = document.getElementById('frametarget');

    // フレームをreload
    iframe.contentWindow.location.reload(true);

}
window.addEventListener('load', function () {

    // 5秒ごとに、フレームをreload
    setInterval(doReloadTheFrame, 5000);

});