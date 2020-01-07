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

DROP TABLE IF EXISTS initial_invocations;

DROP TABLE invocations;

CREATE TABLE invocations (
    id              BIGINT AUTO_INCREMENT PRIMARY KEY,
    customerId      BIGINT                                 NOT NULL,
    applicationId   BIGINT                                 NOT NULL,
    environmentId   BIGINT                                 NOT NULL,
    methodId        BIGINT                                 NOT NULL,
    invokedAtMillis BIGINT                                 NOT NULL,
    status          ENUM (
-- @formatter:off
        'NOT_INVOKED',
        'INVOKED',
        'FOUND_IN_PARENT_CLASS',
        'NOT_FOUND_IN_CODE_BASE',
        'EXCLUDED_BY_PACKAGE_NAME',
        'EXCLUDED_BY_VISIBILITY',
        'EXCLUDED_SINCE_TRIVIAL'
-- @formatter:on
        )                                                  NOT NULL COMMENT 'Same values as SignatureStatus',
    createdAt       TIMESTAMP(3) DEFAULT CURRENT_TIMESTAMP NOT NULL,
    timestamp       TIMESTAMP    DEFAULT CURRENT_TIMESTAMP NOT NULL ON UPDATE CURRENT_TIMESTAMP,

    CONSTRAINT ix_invocation_customerId FOREIGN KEY (customerId) REFERENCES customers(id) ON DELETE CASCADE,
    CONSTRAINT ix_invocation_applicationId FOREIGN KEY (applicationId) REFERENCES applications(id) ON DELETE CASCADE,
    CONSTRAINT ix_invocation_environmentId FOREIGN KEY (environmentId) REFERENCES environments(id) ON DELETE CASCADE,
    CONSTRAINT ix_invocation_methodId FOREIGN KEY (methodId) REFERENCES methods(id) ON DELETE CASCADE,

    CONSTRAINT ix_invocation_identity UNIQUE (customerId, applicationId, environmentId, methodId)
);
