--
-- Copyright (c) 2015-2020 Hallin Information Technology AB
--
-- Permission is hereby granted, free of charge, to any person obtaining a copy
-- of this software and associated documentation files (the "Software"), to deal
-- in the Software without restriction, including without limitation the rights
-- to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
-- copies of the Software, and to permit persons to whom the Software is
-- furnished to do so, subject to the following conditions:
--
-- The above copyright notice and this permission notice shall be included in
-- all copies or substantial portions of the Software.
--
-- THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
-- IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
-- FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
-- AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
-- LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
-- OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
-- THE SOFTWARE.
--

CREATE TABLE codebase_fingerprints
(
    id                  BIGINT AUTO_INCREMENT PRIMARY KEY,
    customerId          BIGINT                                 NOT NULL,
    applicationId       BIGINT                                 NOT NULL,
    codeBaseFingerprint VARCHAR(200)                           NOT NULL,
    createdAt           TIMESTAMP    DEFAULT CURRENT_TIMESTAMP NOT NULL,
    publishedAt         TIMESTAMP(3) DEFAULT CURRENT_TIMESTAMP NOT NULL ON UPDATE CURRENT_TIMESTAMP,

    CONSTRAINT ix_codebase_fingerprints_identity UNIQUE (customerId, applicationId, codeBaseFingerprint),
    CONSTRAINT ix_codebase_fingerprints_customerId FOREIGN KEY (customerId) REFERENCES customers (id) ON DELETE CASCADE,
    CONSTRAINT ix_codebase_fingerprints_applicationId FOREIGN KEY (applicationId) REFERENCES applications (id) ON DELETE CASCADE
);