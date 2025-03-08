CREATE TABLE IF NOT EXISTS file_uploads
(
    id INTEGER PRIMARY KEY AUTOINCREMENT,
    file_name TEXT NULL,
    original_file_name TEXT NULL,
    status TEXT NULL DEFAULT 'UPLOADED',
    updated_at TIMESTAMP DEFAULT (datetime('now', 'localtime')),
    create_at TIMESTAMP DEFAULT (datetime('now', 'localtime'))
);

CREATE TABLE IF NOT EXISTS info_collection
(
    id INTEGER PRIMARY KEY AUTOINCREMENT,
    file_id INTEGER NOT NULL,
    email TEXT NULL,
    approver_name TEXT NULL,
    team TEXT NULL,
    approving_date TEXT NULL,
    approving_conclusion TEXT NULL,
    additional_comments TEXT NULL,
    comments TEXT NULL,
    updated_at TEXT TIMESTAMP DEFAULT (datetime('now', 'localtime')),
    create_at TEXT TIMESTAMP DEFAULT (datetime('now', 'localtime')),
    FOREIGN KEY (file_id) REFERENCES file_uploads(id)
);

CREATE TABLE IF NOT EXISTS scan_logs
(
    id INTEGER PRIMARY KEY AUTOINCREMENT,
    file_id INTEGER NOT NULL,
    log_msg TEXT NULL,
    updated_at TEXT TIMESTAMP DEFAULT (datetime('now', 'localtime')),
    create_at TEXT TIMESTAMP DEFAULT (datetime('now', 'localtime')),
    FOREIGN KEY (file_id) REFERENCES file_uploads(id)
    );