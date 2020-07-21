---tasks

---1

---2
SELECT author.name FROM news N 
LEFT JOIN news_author NA ON na.news_id = n.id
LEFT JOIN author ON author.id = na.author_id
GROUP BY author.name
HAVING AVG(LENGTH(N.title)) > 500
AND SUM(LENGTH(N.title)) > 3000;

---3
CREATE FUNCTION get_all_tags_for_news(newsID IN NUMBER, separator IN CHAR) 
   RETURN VARCHAR2
   IS tags VARCHAR2(32760);
   BEGIN
   SELECT LISTAGG(tag, separator) AS CONCATDATA INTO tags
FROM tag JOIN news_tag ON tag.id = tag_id WHERE news_tag.news_id = newsID;
RETURN(tags); 
END;
SELECT get_all_tags_for_news(1,',') FROM dual;

---4
SELECT id, title, get_all_tags_for_news(id, ' ') AS TAGS FROM news;

WITH TMP AS (
 SELECT ROW_NUMBER() OVER(PARTITION BY news.id ORDER BY news.id) RN,
 news.id, news.title, LISTAGG(TAG, ', ') WITHIN GROUP (ORDER BY news.id) OVER(PARTITION BY NEWS.ID) TAGLIST
 FROM news
 LEFT JOIN news_tag ON news_id = news.id
 LEFT JOIN tag ON tag_id = tag.id
) SELECT * FROM TMP WHERE RN <= 1

---5
WITH AUTHOR_WITH_RND_RN AS (
    SELECT ROW_NUMBER()OVER(ORDER BY DBMS_RANDOM.RANDOM) AS RN, name, id FROM author
) 
SELECT A1.name AS PARTICIPANT1, A2.name AS PARTICIPANT2
FROM AUTHOR_WITH_RND_RN A1 JOIN AUTHOR_WITH_RND_RN A2 ON (A1.RN = A2.RN)

---6

---7
select
"Reserved_Space(MB)", "Reserved_Space(MB)" - "Free_Space(MB)" "Used_Space(MB)","Free_Space(MB)"
from(
select
(select sum(bytes/(1024*1024)) from dba_data_files) "Reserved_Space(MB)",
(select sum(bytes/(1024*1024)) from dba_free_space) "Free_Space(MB)"
from dual );

---8
-- Tables + Size MB
select owner, table_name, round((num_rows*avg_row_len)/1024) KB from all_tables
where owner = 'EGOIST' and num_rows > 0 order by KB desc;

-- Tables + Rows
select owner, table_name, num_rows from all_tables
where owner = 'EGOIST' and num_rows > 0 order by num_rows desc;


