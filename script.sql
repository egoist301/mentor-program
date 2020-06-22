---tasks

---1

---2

---3
CREATE FUNCTION get_all_tags_for_news(newsID IN NUMBER, separator IN CHAR) 
   RETURN VARCHAR2
   IS tags VARCHAR2(40);
   BEGIN
   SELECT LISTAGG(CONCAT(tag,separator)) AS CONCATDATA INTO tags
FROM tag JOIN news_tag ON tag.id = tag_id WHERE news_tag.news_id = newsID;
RETURN(tags); 
END;
SELECT get_all_tags_for_news(1,',') FROM dual;

---4(edit)
SELECT id, title, get_all_tags_for_news(id, ' ') AS TAGS FROM news;

---5

---6

---7

---8
-- Tables + Size MB
select owner, table_name, round((num_rows*avg_row_len)/(1024*1024)) MB from all_tables
where owner = 'EGOIST' and num_rows > 0 order by MB desc;

-- Tables + Rows
select owner, table_name, num_rows from all_tables
where owner = 'EGOIST' and num_rows > 0 order by num_rows desc;


