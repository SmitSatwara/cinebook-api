-- =============================================
-- CineBook Sample Data
-- Run on every startup safely - duplicates ignored
-- =============================================

-- THEATRES (6 total - 2 with same name+city, different address)
INSERT INTO theatres (name, city, address)
VALUES ('PVR Cinemas', 'Ahmedabad', 'SG Highway, Prahlad Nagar'),
       ('PVR Cinemas', 'Ahmedabad', 'Achlaj Mall, Satellite'),
       ('INOX Movies', 'Surat', 'VR Mall, Dumas Road'),
       ('Cinepolis', 'Vadodara', 'Alkapuri, RC Dutt Road'),
       ('INOX Movies', 'Rajkot', 'Yagnik Road, Kalawad Road'),
       ('MovieMax', 'Gandhinagar', 'Infocity, Sector 22') ON CONFLICT DO NOTHING;

-- SCREENS (2 per theatre = 12 total)
INSERT INTO screens (name, total_seats, theatre_id)
VALUES ('Screen 1', 6, 1),
       ('Screen 2', 6, 1),
       ('Screen 1', 6, 2),
       ('Screen 2', 6, 2),
       ('Screen 1', 6, 3),
       ('Screen 2', 6, 3),
       ('Screen 1', 6, 4),
       ('Screen 2', 6, 4),
       ('Screen 1', 6, 5),
       ('Screen 2', 6, 5),
       ('Screen 1', 6, 6),
       ('Screen 2', 6, 6) ON CONFLICT DO NOTHING;

-- SEATS (6 per screen = 72 total, 3 REGULAR + 3 PREMIUM)
INSERT INTO seats (seat_number, seat_type, screen_id)
VALUES
-- Screen 1 (Theatre 1 - PVR SG Highway)
('A1', 'REGULAR', 1),
('A2', 'REGULAR', 1),
('A3', 'REGULAR', 1),
('P1', 'PREMIUM', 1),
('P2', 'PREMIUM', 1),
('P3', 'PREMIUM', 1),
-- Screen 2 (Theatre 1 - PVR SG Highway)
('A1', 'REGULAR', 2),
('A2', 'REGULAR', 2),
('A3', 'REGULAR', 2),
('P1', 'PREMIUM', 2),
('P2', 'PREMIUM', 2),
('P3', 'PREMIUM', 2),
-- Screen 3 (Theatre 2 - PVR Achlaj)
('A1', 'REGULAR', 3),
('A2', 'REGULAR', 3),
('A3', 'REGULAR', 3),
('P1', 'PREMIUM', 3),
('P2', 'PREMIUM', 3),
('P3', 'PREMIUM', 3),
-- Screen 4 (Theatre 2 - PVR Achlaj)
('A1', 'REGULAR', 4),
('A2', 'REGULAR', 4),
('A3', 'REGULAR', 4),
('P1', 'PREMIUM', 4),
('P2', 'PREMIUM', 4),
('P3', 'PREMIUM', 4),
-- Screen 5 (Theatre 3 - INOX Surat)
('A1', 'REGULAR', 5),
('A2', 'REGULAR', 5),
('A3', 'REGULAR', 5),
('P1', 'PREMIUM', 5),
('P2', 'PREMIUM', 5),
('P3', 'PREMIUM', 5),
-- Screen 6 (Theatre 3 - INOX Surat)
('A1', 'REGULAR', 6),
('A2', 'REGULAR', 6),
('A3', 'REGULAR', 6),
('P1', 'PREMIUM', 6),
('P2', 'PREMIUM', 6),
('P3', 'PREMIUM', 6),
-- Screen 7 (Theatre 4 - Cinepolis Vadodara)
('A1', 'REGULAR', 7),
('A2', 'REGULAR', 7),
('A3', 'REGULAR', 7),
('P1', 'PREMIUM', 7),
('P2', 'PREMIUM', 7),
('P3', 'PREMIUM', 7),
-- Screen 8 (Theatre 4 - Cinepolis Vadodara)
('A1', 'REGULAR', 8),
('A2', 'REGULAR', 8),
('A3', 'REGULAR', 8),
('P1', 'PREMIUM', 8),
('P2', 'PREMIUM', 8),
('P3', 'PREMIUM', 8),
-- Screen 9 (Theatre 5 - INOX Rajkot)
('A1', 'REGULAR', 9),
('A2', 'REGULAR', 9),
('A3', 'REGULAR', 9),
('P1', 'PREMIUM', 9),
('P2', 'PREMIUM', 9),
('P3', 'PREMIUM', 9),
-- Screen 10 (Theatre 5 - INOX Rajkot)
('A1', 'REGULAR', 10),
('A2', 'REGULAR', 10),
('A3', 'REGULAR', 10),
('P1', 'PREMIUM', 10),
('P2', 'PREMIUM', 10),
('P3', 'PREMIUM', 10),
-- Screen 11 (Theatre 6 - MovieMax Gandhinagar)
('A1', 'REGULAR', 11),
('A2', 'REGULAR', 11),
('A3', 'REGULAR', 11),
('P1', 'PREMIUM', 11),
('P2', 'PREMIUM', 11),
('P3', 'PREMIUM', 11),
-- Screen 12 (Theatre 6 - MovieMax Gandhinagar)
('A1', 'REGULAR', 12),
('A2', 'REGULAR', 12),
('A3', 'REGULAR', 12),
('P1', 'PREMIUM', 12),
('P2', 'PREMIUM', 12),
('P3', 'PREMIUM', 12) ON CONFLICT DO NOTHING;

-- MOVIES (6 total - 2 English, 2 Hindi, 2 Gujarati)
INSERT INTO movies (title, genre, language, duration, rating, release_date, description)
VALUES ('Inception', 'Thriller', 'ENGLISH', 148, 8.8, '2010-07-16',
        'A thief who steals corporate secrets through dream-sharing technology.'),
       ('The Dark Knight', 'Action', 'ENGLISH', 152, 9.0, '2008-07-18',
        'Batman faces the Joker, a criminal mastermind who wants to plunge Gotham into anarchy.'),
       ('3 Idiots', 'Comedy', 'HINDI', 170, 8.4, '2009-12-25',
        'Two friends search for their long lost companion while recollecting their college days.'),
       ('Dangal', 'Drama', 'HINDI', 161, 8.3, '2016-12-23',
        'Former wrestler trains his daughters to become world-class wrestlers.'),
       ('Chhello Divas', 'Comedy', 'GUJARATI', 155, 7.8, '2015-11-20',
        'Seven college friends enjoy their last days of college life in Surat.'),
       ('Hellaro', 'Drama', 'GUJARATI', 132, 8.1, '2019-02-22',
        'A group of women find liberation through the folk dance Garba.') ON CONFLICT DO NOTHING;