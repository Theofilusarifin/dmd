<?php
header('Access-Control-Allow-Origin: *');

$mysqli = new mysqli("localhost", "root", "", "dmd");

if ($mysqli->connect_errno) {
    echo "Failed to connect to MySQL: " . $mysqli->connect_error;
}

// Default Status and Message
$status = 'error';
$msg = 'Register error!';
$user_id = 0;

if (isset($_POST['username']) && isset($_POST['first_name']) && isset($_POST['password'])) {
    $username = $_POST['username'];
    $password = $_POST['password'];
    $first_name = $_POST['first_name'];
    $last_name = isset($_POST['last_name']) ? $_POST['last_name'] : null;

    // Query
    $sql = "SELECT * FROM users WHERE username = ?";
    $stmt = $mysqli->prepare($sql);
    $stmt->bind_param("s", $username);
    $stmt->execute();
    $res = $stmt->get_result();
    $row = $res->fetch_assoc();

    // If there is no data that matched
    if (!$row) {
        $sql = "INSERT INTO users (username, first_name, last_name, password) VALUES (?, ?, ?, ?)";
        $stmt = $mysqli->prepare($sql);
        $stmt->bind_param("ssss", $username, $first_name, $last_name, $password);
        if ($stmt->execute()) {
            // Insert successful
            $status = 'success';
            $msg = "Registration succesful!";
            $user_id = $mysqli->insert_id;
        } else {
            $status = 'error';
            $msg = "Registration failed!";
        }
    }
    // There is data that matched 
    else {
        $status = 'error';
        $msg = 'Username is already used!';
    }
}

// Return Json
echo json_encode(array(
    "status" => $status,
    "msg" => $msg,
    "user_id" => $user_id,
));
