<?php
header('Access-Control-Allow-Origin: *');

$mysqli = new mysqli("localhost", "native_160420108", "ubaya", "native_160420108");

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
        $sql = "INSERT INTO users (username, first_name, last_name, password, registration_date) VALUES (?, ?, ?, ?, ?)";

        // Add 7 hour because gmt+7
        $now = date("Y-m-d H:i:s");
        $registration_date = date("Y-m-d H:i:s", strtotime($now . ' + 7 hours'));

        $stmt = $mysqli->prepare($sql);
        $stmt->bind_param("sssss", $username, $first_name, $last_name, $password, $registration_date);
        if ($stmt->execute()) {
            $user_id = $mysqli->insert_id;
            // Update image url
            $url_img = "https://dmdproject02.000webhostapp.com/photo/" . $user_id . ".jpg";
            $sql = "UPDATE users SET url_img = ? WHERE id = ?";
            $stmt = $mysqli->prepare($sql);
            $stmt->bind_param("si", $url_img, $user_id);
            // Execute update image
            if ($stmt->execute()) {
                // Insert successful
                $status = 'success';
                $msg = "Registration succesful!";
            }
            else{
                $status = 'error';
                $msg = "Registration failed!";
            }
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
