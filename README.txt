Twitter Clone. Uses JSP/Servlet/Beans, Javascript/JQuery, AJAX and MySQL.  Implemented with a RESTful interface and MVC architecture.

Restful Interface:

 * GET: /profile - Shows user's profile, including all your's and following's messages in a feed.  Allows to post message also. Displays as profile.jsp
 * GET /profile/<userEmail> - Show specific user's profile (or 404) 
 * DELETE /profile - Delete account (Interface not really working.  See comment above script in profile.jsp)

 * GET /messages - Returns list of all users and following's messages.
 * GET /messages/<id> - returns specific message (or 404)
 * GET /messages/<user email> - returns list of messages from specific user (or 404)
 * POST /messages - creates a new message from logged-in user at current time
 * PUT /messages - Same as POST
 * DELETE /messages/<id> - delete specific message (only if created from logged-in user) (or 404)

 * GET /followers - Returns list of all users following the currently logged in user

 * GET /following - Returns list of all users, showing people you're following.
 * POST /following - Add a user to people you're following
 * PUT /following - Same as POST
 * DELETE /following/<userEmail> - delete specific user you're following (stop following them) (or 404)

 * GET /login - Displays login page 
 * POST /login - Authenticates user and logs them in to their profile (otherwise redirects back to login)

 * GET /logout - Close session and display logout page (logout.jsp)

 * GET /register - Displays register page (register.jsp)
 * POST /register - Attempts to register user with Chatter, by adding to database.  Redirects back if email address already registered.