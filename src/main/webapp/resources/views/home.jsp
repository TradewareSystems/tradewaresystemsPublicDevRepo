<%@taglib prefix="t" tagdir="/WEB-INF/tags"%>

<t:simpleTemplate>
	<jsp:attribute name="body_area">
		
        <div class="homePageLeftPanel">
       <div class="homePageRightTopPanel">
				 <div class="simpleHeader">
					<b>Interactive data API - Access token</b>
				</div>
				<form action="/kiteLogin" method="GET">
            		<div class="pad-t-10">Admin User Zerodha Id : 
            			<select name="userId">
    						<option value="AF7508">AF7508 - Srinivas</option>
   							<option value="ZI7952">ZI7952 - Niranjan</option>
						</select>
					</div>
           			 <br>
            		<input type="submit" value="Submit" />
        		</form>
        		<br><br>
        		<form action="/createSession" method="get">
            		<div class="pad-t-10">Admin User Zerodha Id : 
            			<select name="userId">
    						<option value="AF7508">AF7508 - Srinivas</option>
   							<option value="ZI7952">ZI7952 - Niranjan</option>
						</select>
					</div>
					<div class="pad-t-10">Access Token : </div>
            		<input type="text" name="accessToken"
						class="textFieldWidth_300" />
           			 <br>
            		<input type="submit" value="Submit" />
        		</form>
        		</div>
        		
        		<div class="homePageRightBottmPanel">
				  <div class="simpleHeader">
					<b>Historical data API - Access token</b>
				</div>
				<form action="/kiteLoginHist" method="get">
            		<div class="pad-t-10">Admin User Zerodha Id : 
            			<select name="userId">
    						<option value="AF7508">AF7508 - Srinivas</option>
   							
						</select>
					</div>
           			 <br>
            		<input type="submit" value="Submit" />
        		</form>
        		<br><br>
        		<form action="/createSessionHist" method="get">
            		<div class="pad-t-10">Admin User Zerodha Id : 
            			<select name="userId">
    						<option value="AF7508">AF7508 - Srinivas</option>
   							
						</select>
					</div>
					<div class="pad-t-10">Access Token : </div>
            		<input type="text" name="accessToken"
						class="textFieldWidth_300" />
           			 <br>
            		<input type="submit" value="Submit" />
        		</form>
        </div>
		</div>
	</jsp:attribute>
</t:simpleTemplate>