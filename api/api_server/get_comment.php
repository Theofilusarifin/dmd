<?php
header('Access-Control-Allow-Origin: *');

$mysqli = new mysqli("localhost", "native_160420108", "ubaya", "native_160420108");
if ($mysqli->connect_errno) {
    echo "Failed to connect to MySQL: " . $mysqli->connect_error;
}

// Default Status and Message
$status = 'error';
$msg = 'Get Comment error!';
$memes = [];
if (isset($_POST['meme_id'])) {
    // Get passed variable
    $meme_id = $_POST['meme_id'];

    // Get all comment detail
    $sql =  "SELECT c.*, u.first_name, u.last_name, u.privacy_setting FROM memes m LEFT JOIN comments c on m.id = c.meme_id INNER JOIN users u on u.id = c.user_id WHERE m.id = ? ORDER BY c.created_at";
    $stmt = $mysqli->prepare($sql);
    $stmt->bind_param("i", $meme_id);
    $stmt->execute();
    $res = $stmt->get_result();

    $comments = [];
    while ($row = $res->fetch_assoc()) {
        // Store comment details to array
        $comment = array(
            "id" => $row['id'],
            "user_id" => $row['user_id'],
            "first_name" => $row['first_name'],
            "last_name" => $row['last_name'],
            "privacy_setting" => $row['privacy_setting'],
            "meme_id" => $row['meme_id'],
            "content" => $row['content'],
            "created_at" => $row['created_at'],
        );
        $comments[] = $comment;
    }

    // Set success status 
    $status = 'success';
    $msg = 'Get comments successful!';
}
// Return Json
echo json_encode(array(
    "status" => $status,
    "msg" => $msg,
    "comments" => $comments,
));
