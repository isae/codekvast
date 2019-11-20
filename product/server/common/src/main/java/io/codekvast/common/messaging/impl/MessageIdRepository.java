/*
 * Copyright (c) 2015-2019 Hallin Information Technology AB
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */
package io.codekvast.common.messaging.impl;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

/**
 * A repository (DAO) for keeping track of which messageIds that have been processed.
 *
 * @author olle.hallin@crisp.se
 */
@Repository
@RequiredArgsConstructor
@Slf4j
public class MessageIdRepository {

    private final JdbcTemplate jdbcTemplate;

    @Transactional(readOnly = true)
    public boolean isDuplicate(@NonNull String messageId) {
        Integer count = jdbcTemplate.queryForObject("SELECT COUNT(*) FROM rabbitmq_message_ids WHERE messageId = ?", Integer.class, messageId);
        if (count != 0) {
            logger.info("MessageId {} has already been processed", messageId);
        }
        return count != 0;
    }


    @Transactional
    public void remember(@NonNull String messageId) {
        int inserted = jdbcTemplate.update("INSERT INTO rabbitmq_message_ids(messageId) VALUE (?)", messageId);
        if (inserted != 1) {
            logger.error("Failed to remember messageId {}", messageId);
        } else {
            logger.debug("Remembered messageId {}", messageId);
        }
    }
}