# React Field Agent Assessment

## Tasks

_TODO_ Add time estimates to each of the top-level tasks

* [ ] Continue working in the repo from last week's Field Agent API repository (#.# hours)
  * [ ] Add a README in the `client` folder with the contents from this file

* [ ] Review the requirements (#.# hours)

* [ ] Identify any research that I need to do (#.# hours)

### Part 1: Project Setup and Agents List

* [ ] Create a new React project with CRA (create-react-app)
  * [ ] Remove the cruft (refer back to the Components and JSX exercise for instructions)

* [ ] Add Bootstrap (or other CSS framework) to the `public/index.html` file
  * [ ] Add a link to the Bootstrap CSS using the [CDN from the official docs](https://getbootstrap.com/docs/4.6/getting-started/introduction/#css)
  * [ ] Add the [`container` CSS class](https://getbootstrap.com/docs/4.6/layout/overview/#containers) to the `<div id="root"></div>` element

* [ ] Create `Agents` component (stub)
  * [ ] Update `App` component to render `Agents`

* [ ] Update `Agents` to render list of agents
  * [ ] Use `fetch` to `GET` a list of agents from the Field Agent API when the component is first loaded
  * [ ] Write JSX to render the agents array
  * [ ] Stub out click event handlers ("Add Agent", "Edit Agent", "Delete Agent") as necessary

**Commit all changes and push to GitHub**

### Part 2: Add Agent and Delete Agent

* [ ] Create a form to add an agent
  * [ ] Add form JSX
  * [ ] Decide between using individual state variables for input elements or a single object
  * [ ] Add onChange event handlers to input elements
  * [ ] Add onSubmit event handler to form element (be sure to prevent the form from submitting!)
  * [ ] Create agent object
  * [ ] Use `fetch` to `POST` the new agent's information to the Field Agent API
  * [ ] On success, update the agents array (don't modify the original array!), or on failure, display any validation errors from the API in the UI

* [ ] Support deleting agents
  * [ ] Confirm the deletion with the user
  * [ ] Use `fetch` to `DELETE` the agent from the Field Agent API
  * [ ] On success, update the agents array (don't modify the original array!)

* [ ] Conditionally render sections of the component
  * [ ] Add state variable to track the current view
  * [ ] Add conditional logic to the JSX to display the appropriate view

**Commit all changes and push to GitHub**

### Part 3: Edit Agent

* [ ] Support editing agents
  * [ ] Store the "edit agent ID" in a new state variable
  * [ ] Retrieve the agent to edit
  * [ ] Update form state variable(s)
  * [ ] Add form JSX
  * [ ] Add onChange event handlers to input elements
  * [ ] Add onSubmit event handler to form element (be sure to prevent the form from submitting!)
  * [ ] Create agent object
  * [ ] Use `fetch` to `PUT` the updated agent's information to the Field Agent API
  * [ ] On success, update the agents array (don't modify the original array!), or on failure, display any validation errors from the API in the UI

* [ ] Apply Bootstrap styling (as needed)
  * [ ] Update the agents list
  * [ ] Update the add agent form
  * [ ] Update the edit agent form
  * [ ] Update the delete agent confirmation

**Commit all changes and push to GitHub**

### Part 4: Client-Side Routes

* [ ] Implement the required client-side routes (#.# hours)
  * [ ] Install `react-router-dom`
  * [ ] Define the necessary client-side routes (see the list of routes below)
  * [ ] Stub out any components that are needed to support the client-side routes
    * _Note: Stub out the individual Agents CRUD UI components but hold off on moving any code from last week's monolithic Agents CRUD UI component to the individual components_
  * [ ] Display a "Not Found" message if a route doesn't match one of the defined routes

### Part 5: Agents CRUD UI Component Refactoring

* [ ] Update the "Agents" list component (#.# hours)
  * [ ] Update the "Add Agent" button to redirect the user to the "Add Agent" route (if not already completed)
  * [ ] Update the individual agent "Edit" buttons to redirect the user to the appropriate route (if not already implemented)

* [ ] Update the "Add Agent" form component (#.# hours)
  * [ ] Move code from the "Agents" list component into the "Add Agent" form component
  * [ ] After a successful `POST` to the Field Agent API, redirect the user to the "Agents" route

* [ ] Update the "Edit Agent" form component (#.# hours)
  * [ ] Move code from the "Agents" list component into the "Edit Agent" form component
  * [ ] Use the `useParams` hook to get the agent's ID from the route
  * [ ] Use `fetch` to `GET` the agent from the Field Agent API when the component is first loaded
  * [ ] After a successful `PUT` to the Field Agent API, redirect the user to the "Agents" route

_Note: A single form component can be used for both "Add Agent" and "Edit Agent"._

## High-Level Requirements

* Implement a full CRUD UI for agents (display, add, update, and delete).
* Implement the required client-side routes.
* Display a "Not Found" message if a route doesn't match one of the defined routes.
* Create React components as needed to support the required client-side routes.

## Technical Requirements

* Use Create React App.
* Use `fetch` for async HTTP.
* Use React Router to implement the client-side routes.
* Use React Router's `useHistory` hook to programmatically redirect users and `useParams` hook to access parameters, paths, and other data.
* You are not allowed to change the Field Agent HTTP Service or database (unless there's a confirmed bug and your instructor approves).
* Use a CSS framework.

## Client-Side Routes

- "Home" `/` - Renders a component that displays a welcome message and a link to the "Agents" route
  - Links to other parts of the website could be added in the future
- "Agents" `/agents` - Renders a component that displays a list of agents
- "Add Agent" `/agents/add` - Renders a component that displays a form to add an agent
- "Edit Agent" `/agents/edit/:id` - Renders a component that displays a form to edit the agent specified by the `:id` route parameter
- "Delete Agent" `/agents/delete/:id` (optional) - Renders a component that displays a confirmation message to delete the agent specified by the `:id` route parameter
  - _Note: If this route isn't implemented, handle agent deletion within the "Agents" route._
- "Not Found" - Renders a component that displays a friendly "not found" message if the requested route doesn't match one of the defined routes
