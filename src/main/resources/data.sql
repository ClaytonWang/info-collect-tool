CREATE TABLE IF NOT EXISTS file_uploads
(
    id INTEGER PRIMARY KEY AUTOINCREMENT,
    file_name TEXT NOT NULL,
    email TEXT NOT NULL,
    updated_at TIMESTAMP DEFAULT (datetime('now', 'localtime')),
    create_at TIMESTAMP DEFAULT (datetime('now', 'localtime'))
);

CREATE TABLE IF NOT EXISTS info_collection
(
    id INTEGER PRIMARY KEY AUTOINCREMENT,
    file_id INTEGER NOT NULL,
    central_name TEXT NOT NULL,
    central_team TEXT NOT NULL,
    approver_email TEXT NOT NULL,
    approving_date TEXT NOT NULL,
    approving_conclusion TEXT NOT NULL,
    comments TEXT NOT NULL,
    updated_at TEXT TIMESTAMP DEFAULT (datetime('now', 'localtime')),
    create_at TEXT TIMESTAMP DEFAULT (datetime('now', 'localtime')),
    FOREIGN KEY (file_id) REFERENCES file_uploads(id)
);