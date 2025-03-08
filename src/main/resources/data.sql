CREATE TABLE IF NOT EXISTS file_uploads
(
    id INTEGER PRIMARY KEY AUTOINCREMENT,
    file_name TEXT NOT NULL,
    original_file_name TEXT NOT NULL,
    updated_at TIMESTAMP DEFAULT (datetime('now', 'localtime')),
    create_at TIMESTAMP DEFAULT (datetime('now', 'localtime'))
);

CREATE TABLE IF NOT EXISTS info_collection
(
    id INTEGER PRIMARY KEY AUTOINCREMENT,
    file_id INTEGER NOT NULL,
    email TEXT NOT NULL,
    approver_name TEXT NOT NULL,
    team TEXT NOT NULL,
    approving_date TEXT NOT NULL,
    approving_conclusion TEXT NOT NULL,
    additional_comments TEXT NOT NULL,
    comments TEXT NOT NULL,
    updated_at TEXT TIMESTAMP DEFAULT (datetime('now', 'localtime')),
    create_at TEXT TIMESTAMP DEFAULT (datetime('now', 'localtime')),
    FOREIGN KEY (file_id) REFERENCES file_uploads(id)
);

CREATE TABLE IF NOT EXISTS scan_logs
(
    id INTEGER PRIMARY KEY AUTOINCREMENT,
    file_id INTEGER NOT NULL,
    status TEXT NOT NULL,
    error_msg TEXT NOT NULL,
    updated_at TEXT TIMESTAMP DEFAULT (datetime('now', 'localtime')),
    create_at TEXT TIMESTAMP DEFAULT (datetime('now', 'localtime')),
    FOREIGN KEY (file_id) REFERENCES file_uploads(id)
    );