# jwt-security
jwt-security


1. create a class JwtSecurityConfig which is config class, extends WebSecurityConfigurerAdaptor
in this refer 2--@Bean JwtAuthenticationTokenFilter
						--initialize JwtAuthenticationTokenFilter which be your filter.
					   --for filter,create method for authenticatonManager as setAuthenticationManager
					   --for filter, create a custom  Authetication Success Handler, which is a class as JwtSuccessHandler(write your success msg here)
			@Bean create you rown custom authenticatonManager, inside this use ProviderMaanager which will take your custom AuthenticationProvider(JwtAuthenticationProvider) as param.
			-- implement configure Http security
			   --in above implement session security and make session stateless
			   ---use above created filter JwtAuthenticationTokenFilter in this, before usernamepassword class load.
			 
2. we will create some customs in security
		---JwtAuthenticationTokenFilter extends AbstractAuthenticationProcessingFilter
			--pass a default url here
			--decode token here in @Override attemptauthetication
				--get the header set from postman for request, get header value pass it to JwtAuthenticationToken(3), now use authenticatonManager to authenticate this request using this token
				from JwtAuthenticationToken.
			--@override onsuccessfulAuthetication, in this forward request to next filter after your filter.	
		---JwtAuthenticationProvider extends AbstractUserdetailsAuthenticationProvider
			--it supports and return JwtAuthenticationToken(3) which is class itself.
			--use retreiveUser method and inside that convert token to JwtAuthenticationToken, validate this token using custom jwtvalidatetoken class and take decoded value  to JwtUser object.
			--create JwtUserdeatils  implemets UserDetails, passing it username,toke, authorities,ID
		---	jwtvalidatetoken, create a object method which takes your token, and do claims for validating token using secret key and convert o/p to body. and set values from body to JwtUser.
		---JwtGenerator, generate token using Claims, using JwtsBuilder. To JwtsBuilder pass your secret key with SIgnatureAlgo and claims
		
3. in Model
   ---create JwtAuthenticationToken extends UserNamePasswordAuthenticationtoken, in this we will store token. override principal and credentials methods.
   ---create JwtUser(username, id, role).
   --JwtUserdeatils 
   
4. in controller
	---\token, write a token controller which will generate token using JwtGenerator in a post method with using @pathvariable as username input.
	
	
5. localhost:8080/token,, will give you get not allowed that means u can post request only. same way /rest/hello should not be allowed with "JWT token missing"
6. go to postman , for post request localhost:8080/token , pass body with us,id,role in JSON, u will get a JSON token.
7. for get request, /rest/hello pass authorisation as header and header value will be Token/Space/Your JSON token, on success u will get to access secure rest end.


we created a custom authenticationProvider and passed it to custom authenticationManager
we created a authenticationfilter to check token is valid or not, inside it there will be a success handler.
then through httpsecurity we can configure which URL should be allowed, in this we also handle exception through authenticationEntryPoint which will tell if token is incorrect.
on controller we will create a tokencontroller which will generate token through JwtGenerator, inside this create claims(using username,id,role) which will be coming from io.JSONweb token,
then create a JSON msg using Jwts Builder using algorithm and using secret key.
now using the generated token we should access the rest/hello end point.
In authenticationprovider, we have created some validator using the same library io.JSONweb. In this custom validator you are parsing the extracted web token using the secret key.
By doing so we validated the user, and sending this user to internal Spring security, which we will use jwtuserdetails
