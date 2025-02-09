// Copyright 2022 PingCAP, Inc.
//
// Licensed under the Apache License, Version 2.0 (the "License");
// you may not use this file except in compliance with the License.
// You may obtain a copy of the License at
//
//     http://www.apache.org/licenses/LICENSE-2.0
//
// Unless required by applicable law or agreed to in writing, software
// distributed under the License is distributed on an "AS IS" BASIS,
// WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
// See the License for the specific language governing permissions and
// limitations under the License.

package com.pingcap.ossinsightcoss.util;

import com.pingcap.ossinsightcoss.Config;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Component;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

/**
 * FileUtil
 *
 * @author Icemap
 * @date 2022/10/20
 */
@Component
public class FileUtil {
    @Autowired
    Config config;

    public List<String> readAllExpectFirstLine(String filePath) throws IOException {
        Resource resource = new ClassPathResource(filePath);
        InputStream inputStream = new BufferedInputStream(resource.getInputStream());
        return new BufferedReader(new InputStreamReader(inputStream))
                .lines().skip(1).toList();
    }

    public List<String> readCOSSInvest() {
        try {
            return readAllExpectFirstLine(config.getTablePath());
        } catch (IOException e) {
            e.printStackTrace();
        }
        return new ArrayList<>();
    }

    public List<String> readBaldertonTracked() {
        try {
            return readAllExpectFirstLine(config.getBaldertonTablePath());
        } catch (IOException e) {
            e.printStackTrace();
        }
        return new ArrayList<>();
    }

    public void returnFile(HttpServletResponse response, String fileName, String content) throws IOException {
        response.setContentType("application/octet-stream");
        response.setCharacterEncoding("utf-8");
        response.setHeader("Content-Disposition", "attachment;filename=" + fileName );

        OutputStream os = response.getOutputStream();
        os.write(content.getBytes(StandardCharsets.UTF_8));
        os.close();
    }
}
