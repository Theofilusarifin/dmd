<?php
header('Access-Control-Allow-Origin: *');

$mysqli = new mysqli("localhost", "root", "", "dmd");
if ($mysqli->connect_errno) {
    echo "Failed to connect to MySQL: " . $mysqli->connect_error;
}

// Default Status and Message
$status = 'error';
$msg = 'Get memes error!';
$memes = [];
if (isset($_POST['user_id'])) {
    // Get passed variable
    $user_id = $_POST['user_id'];

    // Get meme id that already liked by login user
    $sql = "SELECT meme_id FROM likes WHERE user_id = ?";
    $stmt = $mysqli->prepare($sql);
    $stmt->bind_param("i", $user_id);
    $stmt->execute();
    $res = $stmt->get_result();

    $meme_liked_id = [];
    // If there is memes that liked by login user
    if ($res->num_rows > 0) {
        while ($row = $res->fetch_assoc()) {
            // Store the meme id in array
            array_push($meme_liked_id, $row['meme_id']);
        }
    }

    // Get all meme detail
    $sql = "SELECT m.*, count(l.meme_id) as total_like FROM memes m LEFT JOIN likes l on m.id = l.meme_id GROUP BY m.id ORDER BY id DESC";
    $stmt = $mysqli->prepare($sql);
    $stmt->execute();
    $res = $stmt->get_result();

    // Get all report detail
    $sql2 = "SELECT m.*, count(r.meme_id) as total_report FROM memes m LEFT JOIN reports r on m.id = r.meme_id GROUP BY m.id ORDER BY m.id DESC";
    $stmt2 = $mysqli->prepare($sql2);
    $stmt2->execute();
    $res2 = $stmt2->get_result();

    // Get all comment detail
    $sql3 =  "SELECT count(c.meme_id) as total_comment FROM memes m LEFT JOIN comments c on m.id = c.meme_id GROUP BY m.id ORDER BY m.id DESC";
    $stmt3 = $mysqli->prepare($sql3);
    $stmt3->execute();
    $res3 = $stmt3->get_result();

    $memes = [];
    while ($row = $res->fetch_assoc()) {
        // get report and comment data
        $row2 = $res2->fetch_assoc();
        $row3 = $res3->fetch_assoc();

        // if meme is already liked by user, set liked to true so user can not like it again
        $liked = (in_array($row['id'], $meme_liked_id)) ? true : false;
        // Store meme details to array
        $meme = array(
            "id" => $row['id'],
            "user_id" => $row['user_id'],
            "url_img" => $row['url_img'],
            "top_text" => $row['top_text'],
            "bottom_text" => $row['bottom_text'],
            "created_at" => $row['created_at'],
            "total_like" => $row['total_like'],
            "total_report" => $row2['total_report'],
            "total_comment" => $row3['total_comment'],
            "liked" => $liked
        );
        $memes[] = $meme;
    }
    // Set success status 
    $status = 'success';
    $msg = 'Get memes successful!';
}
// Return Json
echo json_encode(array(
    "status" => $status,
    "msg" => $msg,
    "memes" => $memes,
));
