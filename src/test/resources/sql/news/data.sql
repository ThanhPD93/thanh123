DELETE FROM injection_result;
DELETE FROM injection_schedule;
DELETE FROM vaccine;
DELETE FROM customer;
DELETE FROM vaccine_type;
DELETE FROM news;
DELETE FROM news_type;

INSERT INTO news_type(news_type_id,description, news_type_name)
VALUES ('NT1','des1','news type 1');

INSERT INTO news(news_id, content, post_date, preview, title, news_type_news_type_id)
VALUES ('N1','content 1','2024-01-01','preview news 1', 'title news 1','NT1');