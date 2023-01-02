<?php
header('Access-Control-Allow-Origin: *');

$mysqli = new mysqli("localhost", "native_160420108", "ubaya", "native_160420108");
if ($mysqli->connect_errno) {
    echo "Failed to connect to MySQL: " . $mysqli->connect_error;
}

// Default Status and Message
$status = 'error';
$msg = 'Get memes error!';
$memes = [];
if (isset($_POST['meme_id'])) {
    // Get passed variable
    $meme_id = $_POST['meme_id'];

    // Get all comment detail
    $sql =  "SELECT * FROM memes where id = ?";
    $stmt = $mysqli->prepare($sql);
    $stmt->bind_param("i", $meme_id);
    $stmt->execute();
    $res = $stmt->get_result();

    $meme = null;
    if ($row = $res->fetch_assoc()) {
        // Store comment details to array
        $meme = array(
            "id" => $row['id'],
            "user_id" => $row['user_id'],
            "url_img" => $row['url_img'],
            "top_text" => $row['top_text'],
            "bottom_text" => $row['bottom_text'],
            "created_at" => $row['created_at'],
        );
        // Set success status 
        $status = 'success';
        $msg = 'Get memes successful!';
    }
    else{
        // Set success status 
        $status = 'error';
        $msg = 'Get memes error!';
    }

}
// Return Json
echo json_encode(array(
    "status" => $status,
    "msg" => $msg,
    "meme" => $meme,
));
