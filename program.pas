program sum;

var
    num1 : integer;
    num2 : integer;
    result : integer;

begin
    writeln('Enter two numbers:');
    readln(num1);
    readln(num2);
    result := num1 + num2;
    writeln('The sum is: ', result);
end.