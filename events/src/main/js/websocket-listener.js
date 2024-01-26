/*
 * @Author: jack ning github@bytedesk.com
 * @Date: 2024-01-25 23:53:50
 * @LastEditors: jack ning github@bytedesk.com
 * @LastEditTime: 2024-01-26 11:47:17
 * @FilePath: /tut-react-and-spring-data-rest/events/src/main/js/websocket-listener.js
 * @Description: 这是默认设置,请设置`customMade`, 打开koroFileHeader查看配置 进行设置: https://github.com/OBKoro1/koro1FileHeader/wiki/%E9%85%8D%E7%BD%AE
 */
'use strict';

const SockJS = require('sockjs-client'); // <1>
require('stompjs'); // <2>

function register(registrations) {
	const socket = SockJS('/payroll'); // <3>
	const stompClient = Stomp.over(socket);
	stompClient.connect({}, function (frame) {
		registrations.forEach(function (registration) { // <4>
			console.log('registration', registration)
			stompClient.subscribe(registration.route, registration.callback);
		});
	});
}

module.exports.register = register;

