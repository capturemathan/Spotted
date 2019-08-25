<?php
$servername = "localhost";
$username = "root";
$password = "";
$dbname = "missing";

$name= $_GET["name"];
$location= $_GET["location"];
//$iden= $_GET["iden"];
//$phone= $_GET["phone"];
//$content= $_GET["content"];

//$age=(int)$age;
//$phone=(int)$phone;

// Create connection
$conn = new mysqli($servername, $username, $password, $dbname);
// Check connection
if ($conn->connect_error) {
    die("Connection failed: " . $conn->connect_error);
} 

$sql = "INSERT INTO nearbymissing (Name,Location)
VALUES ('$name','$location')";

if ($conn->query($sql) === TRUE) {
    echo "New entry created successfully";
} else {
    echo "Error: " . $sql . "<br>" . $conn->error;
}

$conn->close();
?>
