<?php
header('Access-Control-Allow-Origin: *');

$mysqli = new mysqli("localhost", "native_160420108", "ubaya", "native_160420108");

if ($mysqli->connect_errno) {
    echo "Failed to connect to MySQL: " . $mysqli->connect_error;
}

// Default Status and Message
$status = 'error';
$msg = 'Login error!';
$user = null;

if (isset($_POST['username']) && isset($_POST['password'])) {
    $username = $_POST['username'];
    $password = $_POST['password'];

    // Query
    $sql = "SELECT * FROM users WHERE username = ? AND password = ?";
    $stmt = $mysqli->prepare($sql);
    $stmt->bind_param("ss", $username, $password);
    $stmt->execute();
    $res = $stmt->get_result();
    $row = $res->fetch_assoc(); //array

    // If there is no data that matched
    if (!$row) {
        $status = 'error';
        $msg = 'This credential does not match our records!';
    }
    // There is data that matched 
    else {
        $status = 'success';
        $msg = 'Login successful';
        $user = $row;
    }
}

// Return Json
echo json_encode(array(
    "status" => $status,
    "msg" => $msg,
    "user" => $user,
));
