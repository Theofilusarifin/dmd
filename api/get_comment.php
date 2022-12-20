<?php
header('Access-Control-Allow-Origin: *');

$mysqli = new mysqli("localhost", "root", "", "dmd");
if ($mysqli->connect_errno) {
    echo "Failed to connect to MySQL: " . $mysqli->connect_error;
}

// Default Status and Message
$status = 'error';
$msg = 'Get comment error!';

if (isset($_POST['meme_id'])) {
    // Get passed variable
    $meme_id = $_POST['meme_id'];

    // Get all meme comments detail
    $sql = "SELECT c.*, COUNT(lc.comment_id) as total_like FROM comments c LEFT JOIN like_comments lc ON c.id = lc.comment_id WHERE meme_id = ? ORDER BY created_at ASC GROUP BY c.id";
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
            "meme_id" => $row['meme_id'],
            "content" => $row['content'],
            "created_at" => $row['created_at'],
            "total_like" => $row['total_like'],
        );
        $comments[] = $comment;
    }
    // Set success status 
    $status = 'success';
    $msg = $comments;
}

// Return Json
echo json_encode(array(
    "status" => $status,
    "msg" => $msg,
));
