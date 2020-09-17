package com.ssafy.smaheal.controller;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;

import org.apache.commons.exec.CommandLine;
import org.apache.commons.exec.DefaultExecutor;
import org.apache.commons.exec.PumpStreamHandler;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.annotations.ApiOperation;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/api/smile")
public class SmileController {
    
    public static List list = new LinkedList<>();

    @GetMapping("/cameraOn")
    @ApiOperation(value = "웹캠 on")
    public Object cameraOn() throws SQLException, IOException {
        try {
            System.out.println("Camera on Python Call");
            String[] command = new String[4];
            command[0] = "python";
            command[1] = "C:\\AI108\\s03p22b108\\backend\\cameraOn.py";
            command[2] = "10";
            command[3] = "20";
            try {
                execPython(command, "on");
            } catch (Exception e) {
                e.printStackTrace();
            }
            return new ResponseEntity<>(list, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/cameraOff")
    @ApiOperation(value = "웹캠 off")
    public Object cameraOff() throws SQLException, IOException {
        try {
            System.out.println("Camera off Python Call");
            String[] command = new String[2];
            command[0] = "python";
            command[1] = "C:\\AI108\\s03p22b108\\backend\\cameraOff.py";
            try {
                execPython(command, "off");
            } catch (Exception e) {
                e.printStackTrace();
            }
            return new ResponseEntity<>(null, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    public static void execPython(String[] command, String flag) throws IOException, InterruptedException {
        CommandLine commandLine = CommandLine.parse(command[0]);
        for (int i = 1, n = command.length; i < n; i++) {
            commandLine.addArgument(command[i]);
        }

        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        PumpStreamHandler pumpStreamHandler = new PumpStreamHandler(outputStream);
        DefaultExecutor executor = new DefaultExecutor();
        executor.setStreamHandler(pumpStreamHandler);
        int result = executor.execute(commandLine);
        System.out.println("result: " + result);
        
        if(flag == "on") {
            String[] outputList = outputStream.toString().split("\n");
            int len = outputList.length;
            String fileName = outputList[len - 2].trim();
            String percent = outputList[len - 1].trim();
            list.clear();
            list.add(fileName);
            list.add(percent);
            System.out.println(fileName);
            System.out.println(percent);
            // System.out.println("output: " + outputStream.toString().split("\n")[0]);
        }
    }
}
