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
if (isset($_POST['user_id']) && isset($_POST['sort_by'])) {
    // Get passed variable
    $user_id = $_POST['user_id'];
    $sort_by = $_POST['sort_by'];

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

    // Get meme id that already reported by login user
    $sql = "SELECT meme_id FROM reports WHERE user_id = ?";
    $stmt = $mysqli->prepare($sql);
    $stmt->bind_param("i", $user_id);
    $stmt->execute();
    $res = $stmt->get_result();

    $meme_reported_id = [];
    // If there is memes that reported by login user
    if ($res->num_rows > 0) {
        while ($row = $res->fetch_assoc()) {
            // Store the meme id in array
            array_push($meme_reported_id, $row['meme_id']);
        }
    }

    // Get meme like detail
    $meme_likes = [];
    $sql = "SELECT m.*, count(l.meme_id) as total_like FROM memes m LEFT JOIN likes l on m.id = l.meme_id GROUP BY m.id ORDER BY total_like DESC";
    $stmt = $mysqli->prepare($sql);
    $stmt->execute();
    $res = $stmt->get_result();
    if ($res->num_rows > 0) {
        while ($row = $res->fetch_assoc()) {
            $meme = array(
                "id" => $row['id'],
                "user_id" => $row['user_id'],
                "url_img" => $row['url_img'],
                "top_text" => $row['top_text'],
                "bottom_text" => $row['bottom_text'],
                "created_at" => $row['created_at'],
                "total_like" => $row['total_like'],
            );
            $meme_likes[] = $meme;
        }
    }

    // Get all report detail
    $meme_reports = [];
    $sql = "SELECT m.*, count(r.meme_id) as total_report FROM memes m LEFT JOIN reports r on m.id = r.meme_id GROUP BY m.id ORDER BY created_at DESC";
    $stmt = $mysqli->prepare($sql);
    $stmt->execute();
    $res = $stmt->get_result();
    if ($res->num_rows > 0) {
        while ($row = $res->fetch_assoc()) {
            $meme = array(
                "id" => $row['id'],
                "user_id" => $row['user_id'],
                "url_img" => $row['url_img'],
                "top_text" => $row['top_text'],
                "bottom_text" => $row['bottom_text'],
                "created_at" => $row['created_at'],
                "total_report" => $row['total_report'],
            );
            $meme_reports[] = $meme;
        }
    }

    // Get all comment detail
    $meme_comments = [];
    $sql = "SELECT m.*, count(c.meme_id) as total_comment FROM memes m LEFT JOIN comments c on m.id = c.meme_id GROUP BY m.id ORDER BY total_comment DESC";
    $stmt = $mysqli->prepare($sql);
    $stmt->execute();
    $res = $stmt->get_result();
    if ($res->num_rows > 0) {
        while ($row = $res->fetch_assoc()) {
            $meme = array(
                "id" => $row['id'],
                "user_id" => $row['user_id'],
                "url_img" => $row['url_img'],
                "top_text" => $row['top_text'],
                "bottom_text" => $row['bottom_text'],
                "created_at" => $row['created_at'],
                "total_comment" => $row['total_comment'],
            );
            $meme_comments[] = $meme;
        }
    }

    $memes = [];
    if ($sort_by == "Newest") {
        for ($i = 0; $i < count($meme_reports); $i++) {
            // If meme is not reported the display the meme
            if (!in_array($meme_reports[$i]['id'], $meme_reported_id)) {
                // if meme is already liked by user, set liked to true so user can not like it again
                $liked = (in_array($meme_reports[$i]['id'], $meme_liked_id)) ? true : false;
                $total_like = $meme_likes[array_search($meme_reports[$i]['id'], array_column($meme_likes, 'id'))]['total_like'];
                $total_comment = $meme_comments[array_search($meme_reports[$i]['id'], array_column($meme_comments, 'id'))]['total_comment'];
                // Make sure num reported below 3 to be added
                if ($meme_reports[$i]['total_report'] < 3) {
                    $meme = array(
                        "id" => $meme_reports[$i]['id'],
                        "user_id" => $meme_reports[$i]['user_id'],
                        "url_img" => $meme_reports[$i]['url_img'],
                        "top_text" => $meme_reports[$i]['top_text'],
                        "bottom_text" => $meme_reports[$i]['bottom_text'],
                        "created_at" => $meme_reports[$i]['created_at'],
                        "total_report" => $meme_reports[$i]['total_report'],
                        "total_like" =>  $total_like,
                        "total_comment" => $total_comment,
                        "liked" => $liked
                    );
                    $memes[] = $meme;
                }
            }
        }
    } else if ($sort_by == "On Trending") {
        for ($i = 0; $i < count($meme_comments); $i++) {
            // If meme is not reported the display the meme
            if (!in_array($meme_comments[$i]['id'], $meme_reported_id)) {
                // if meme is already liked by user, set liked to true so user can not like it again
                $liked = (in_array($meme_comments[$i]['id'], $meme_liked_id)) ? true : false;
                $total_like = $meme_likes[array_search($meme_comments[$i]['id'], array_column($meme_likes, 'id'))]['total_like'];
                $total_report = $meme_reports[array_search($meme_comments[$i]['id'], array_column($meme_reports, 'id'))]['total_report'];
                // Make sure num reported below 3 to be added
                if ($total_report < 3) {
                    $meme = array(
                        "id" => $meme_comments[$i]['id'],
                        "user_id" => $meme_comments[$i]['user_id'],
                        "url_img" => $meme_comments[$i]['url_img'],
                        "top_text" => $meme_comments[$i]['top_text'],
                        "bottom_text" => $meme_comments[$i]['bottom_text'],
                        "created_at" => $meme_comments[$i]['created_at'],
                        "total_comment" => $meme_comments[$i]['total_comment'],
                        "total_like" => $total_like,
                        "total_report" => $total_report,
                        "liked" => $liked
                    );
                    $memes[] = $meme;
                }
            }
        }
    } else if ($sort_by == "Most popular") {
        for ($i = 0; $i < count($meme_likes); $i++) {
            // If meme is not reported the display the meme
            if (!in_array($meme_likes[$i]['id'], $meme_reported_id)) {
                // if meme is already liked by user, set liked to true so user can not like it again
                $liked = (in_array($meme_likes[$i]['id'], $meme_liked_id)) ? true : false;
                $total_comment = $meme_comments[array_search($meme_likes[$i]['id'], array_column($meme_comments, 'id'))]['total_comment'];
                $total_report = $meme_reports[array_search($meme_likes[$i]['id'], array_column($meme_reports, 'id'))]['total_report'];
                // Make sure num reported below 3 to be added
                if ($total_report < 3) {
                    $meme = array(
                        "id" => $meme_likes[$i]['id'],
                        "user_id" => $meme_likes[$i]['user_id'],
                        "url_img" => $meme_likes[$i]['url_img'],
                        "top_text" => $meme_likes[$i]['top_text'],
                        "bottom_text" => $meme_likes[$i]['bottom_text'],
                        "created_at" => $meme_likes[$i]['created_at'],
                        "total_like" => $meme_likes[$i]['total_like'],
                        "total_comment" => $total_comment,
                        "total_report" => $total_report,
                        "liked" => $liked
                    );
                    $memes[] = $meme;
                }
            }
        }
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
