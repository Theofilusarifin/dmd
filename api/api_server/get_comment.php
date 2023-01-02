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
if (isset($_POST['meme_id']) && isset($_POST['user_id'])) {
    // Get passed variable
    $meme_id = $_POST['meme_id'];
    $user_id = $_POST['user_id'];

    // Get all comment detail
    $sql =  "SELECT * FROM like_comments WHERE user_id = ?";
    $stmt = $mysqli->prepare($sql);
    $stmt->bind_param("i", $user_id);
    $stmt->execute();
    $res = $stmt->get_result();

    $comment_liked_id = [];
    while ($row = $res->fetch_assoc()) {
        // Store comment details to array
        array_push($comment_liked_id, $row['comment_id']);
    }

    // Get all comment detail
    $sql =  "SELECT c.*, u.first_name, u.last_name, u.privacy_setting, count(lc.comment_id) as total_like FROM comments c LEFT JOIN like_comments lc on c.id = lc.comment_id INNER JOIN users u on u.id = c.user_id WHERE c.meme_id = ? GROUP BY c.id ORDER BY c.created_at";
    $stmt = $mysqli->prepare($sql);
    $stmt->bind_param("i", $meme_id);
    $stmt->execute();
    $res = $stmt->get_result();

    $comments = [];
    while ($row = $res->fetch_assoc()) {
        // if comment is already liked by user, set liked to true so user can not like it again
        $liked = (in_array($row['id'], $comment_liked_id)) ? true : false;
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
            "total_like" => $row['total_like'],
            "liked" => $liked,
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
