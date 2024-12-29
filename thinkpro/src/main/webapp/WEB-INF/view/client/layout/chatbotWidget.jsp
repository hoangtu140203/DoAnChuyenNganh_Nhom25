<%@page contentType="text/html" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<link href="/client/css/style_chatbot.css" rel="stylesheet">
<div class="widget">
    <div class="chat-popup" id="chatPopup">
        <div class="chat-header" onclick="toggleChat()">💬 Chat Box</div>
        <div class="chat-body" id="chatBody">
            <div class="chat-message bot">
                <p>Hi, How can I help you?</p>
            </div>
        </div>
        <div class="chat-footer">
            <input type="text" id="chatInput" placeholder="Send messsage...">
            <button onclick="sendMessage()">Send</button>
        </div>
    </div>
    <button class="open-chat-btn" onclick="toggleChat()">💬</button>
</div>
<script src="/client/js/script_chatbot.js"></script>