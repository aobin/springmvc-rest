
Printer = function()
{
    this.changeInputValueWhenLoadImg = function () {
        $("img").load(function () {
            $("input").attr("value","bbbbb");
        });
    };

    this.x="x";

    this.test = function (arg1,arg2) {
        console.log("asdfasdf");
        console.log(arg1,arg2);
        console.log(this.x);
    };

};
