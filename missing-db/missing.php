<?php
$servername = "localhost";
$username = "root";
$password = "";
$dbname = "missing";

$name= $_GET["name"];
$age= $_GET["age"];
$iden= $_GET["iden"];
$phone= $_GET["phone"];
//$content= $_GET["content"];

$age=(int)$age;
$phone=(int)$phone;

// Create connection
$conn = new mysqli($servername, $username, $password, $dbname);
// Check connection
if ($conn->connect_error) {
    die("Connection failed: " . $conn->connect_error);
} 

$sql = "INSERT INTO missingtable (Name,Age,Identification,Phone)
VALUES ('$name','$age','$iden','$phone')";

if ($conn->query($sql) === TRUE) {
    echo "New entry created successfully";
} else {
    echo "Error: " . $sql . "<br>" . $conn->error;
}

$conn->close();
?>
