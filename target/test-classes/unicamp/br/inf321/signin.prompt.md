@/src/test/resources/unicamp/br/inf321/generate_tests.prompt.md

Register Account Test:
- Navigate to http://multibags.1dt.com.br/
- Click on My Account in the top header menu
- Click on Register
- Insert in first name field a random string with maximum length of 12 characters
- Insert in last name field a random string with maximum length of 12 characters
- Select in the dropdown field a random country
- Insert in the state/province field a random string with maximum length of 12 characters
- Insert in the Email address field the following pattern "<random_string><random_int>@g.unicamp.br"
- Insert in the password field the value "abcd1234"
- Insert in the repeat password field the value "abcd1234"
- Click on the CREATE AN ACCOUNT button
- Check if the current URL is http://multibags.1dt.com.br/shop/customer/dashboard.html
- Check if the content on the header of the page has "Welcome "
- Click on the "Welcome " menu
- Check if the menu has the following entries:
  - My Account
  - Logout
