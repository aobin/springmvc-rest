
Printer = function(){
    this.changeInputValueWhenLoadImg = function () {
        $("img").load(function () {
            $("input").attr("value","bbbbb");
        });
    }

    function test() {
        console.log("test");
    }

};
