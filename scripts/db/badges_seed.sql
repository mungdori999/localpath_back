-- 배지 시드 데이터 (MySQL)
-- 사용: mysql -h 127.0.0.1 -P 3307 -u mungdori -p localpath < scripts/db/badges_seed.sql
-- 앱 기동 시 BadgeDataSeeder가 동일 데이터를 자동 삽입합니다.

INSERT INTO badges (badge_key, name, description, emoji, region, order_index)
SELECT * FROM (
    SELECT 'mangridan-cafe-master' AS badge_key, '망리단길 카페 마스터' AS name,
           '망리단길의 대표 카페 2곳을 방문하고 인증하세요' AS description,
           '☕' AS emoji, '망리단길' AS region, 0 AS order_index
    UNION ALL SELECT 'mangridan-foodie', '망리단길 미식가',
           '망리단길 분위기 좋은 식당을 방문하고 인증하세요',
           '🍽️', '망리단길', 1
    UNION ALL SELECT 'mangridan-healing', '망리단길 힐링러',
           '망리단길 카페와 식당을 모두 방문하고 인증하세요',
           '🌿', '망리단길', 2
    UNION ALL SELECT 'mangwon-market-explorer', '망원시장 탐험가',
           '망원시장에 방문하고 인증하세요',
           '🏪', '망원시장', 3
    UNION ALL SELECT 'mangwon-food-master', '망원 미식가',
           '망원시장과 동네 맛집 2곳을 방문하고 인증하세요',
           '🥘', '망원시장', 4
    UNION ALL SELECT 'mangwon-night-walker', '망원 야경러',
           '망원 한강 야경 명소 2곳을 방문하고 인증하세요',
           '🌙', '망원', 5
    UNION ALL SELECT 'mangwon-culture', '망원 문화 탐방',
           '망원시장과 문화비축창을 방문하고 인증하세요',
           '🎨', '망원', 6
) AS seed
WHERE NOT EXISTS (
    SELECT 1 FROM badges b WHERE b.badge_key = seed.badge_key
);

INSERT INTO badge_requirements (badge_id, spot_name)
SELECT b.id, r.spot_name
FROM badges b
JOIN (
    SELECT 'mangridan-cafe-master' AS badge_key, '어노브 ANOVE' AS spot_name
    UNION ALL SELECT 'mangridan-cafe-master', '블루보틀 망원'
    UNION ALL SELECT 'mangridan-foodie', '옥동식'
    UNION ALL SELECT 'mangridan-healing', '카페모토'
    UNION ALL SELECT 'mangridan-healing', '옥동식'
    UNION ALL SELECT 'mangwon-market-explorer', '망원시장'
    UNION ALL SELECT 'mangwon-food-master', '망원시장'
    UNION ALL SELECT 'mangwon-food-master', '할매순대국 망원본점'
    UNION ALL SELECT 'mangwon-food-master', '진미식당'
    UNION ALL SELECT 'mangwon-night-walker', '망원한강공원'
    UNION ALL SELECT 'mangwon-night-walker', '망원나들목'
    UNION ALL SELECT 'mangwon-culture', '망원시장'
    UNION ALL SELECT 'mangwon-culture', '문화비축창'
) AS r ON r.badge_key = b.badge_key
WHERE NOT EXISTS (
    SELECT 1
    FROM badge_requirements br
    WHERE br.badge_id = b.id AND br.spot_name = r.spot_name
);
