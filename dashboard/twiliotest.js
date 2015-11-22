// Twilio Credentials 
var accountSid = 'ACb1d606bee7915655d0d506bf54f279b8'; 
var authToken = 'e199f80c1b2b4d04e91643659c8abad7'; 
 
//require the Twilio module and create a REST client 
var client = require('twilio')(accountSid, authToken); 
 
client.messages.create({ 
	to: "6475398669", 
	from: "+16135192924", 
	body: "hi",   
}, function(err, message) { 
	console.log(message.sid); 
});