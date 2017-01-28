/*
 * Copyright (c) 2015-2017 Crisp AB
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
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT.  IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */
package se.crisp.codekvast.agent.daemon.appversion;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.io.File;
import java.util.Collection;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.regex.PatternSyntaxException;

/**
 * A strategy for picking the app version from the name of a file
 * <p>
 * It handles the cases {@code filename somefile-(.*).jar}. The part inside the parenthesis is used as version.
 *</p>
 * @author olle.hallin@crisp.se
 */
@Slf4j
@Component
public class FilenameAppVersionStrategy extends AbstractAppVersionStrategy {

    public FilenameAppVersionStrategy() {
        super("filename", "pattern");
    }

    @Override
    public boolean canHandle(String[] args) {
        return args != null && args.length == 2 && recognizes(args[0]);
    }

    @Override
    public String resolveAppVersion(Collection<File> codeBases, String[] args) {
        try {
            Pattern pattern = Pattern.compile(args[1]);
            for (File codeBaseFile : codeBases) {
                String version = search(codeBaseFile, pattern);
                if (version != null) {
                    return version;
                }
            }
            log.error("Cannot resolve {} {}: pattern not matched", args[0], args[1]);
        } catch (PatternSyntaxException e) {
            log.error("Cannot resolve {} {}: illegal syntax for {}", args[0], args[1], Pattern.class.getName());
        }
        return UNKNOWN_VERSION;
    }

    private String search(File dir, Pattern pattern) {
        if (!dir.isDirectory()) {
            log.warn("{} is not a directory", dir);
            return null;
        }

        File[] files = dir.listFiles();

        if (files != null) {
            for (File file : files) {
                if (file.isFile()) {
                    Matcher matcher = pattern.matcher(file.getName());
                    if (matcher.matches()) {
                        String version = matcher.group(matcher.groupCount());
                        log.debug("Found version '{}' in {}", version, file);
                        return version;
                    }
                }
            }
            for (File file : files) {
                if (file.isDirectory()) {
                    String version = search(file, pattern);
                    if (version != null) {
                        return version;
                    }
                }
            }
        }
        return null;
    }

}
