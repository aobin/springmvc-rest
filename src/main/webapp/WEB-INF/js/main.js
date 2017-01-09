Main = function(){
    var printer = new Printer();
    printer.changeInputValueWhenLoadImg();

    var a = {"aa":"aa","x":"ax"};
    console.log(a);
    console.log(a.aa);
    printer.test.call(a,1,2);



};
