import { BrowserRouter as Router, Switch, Route } from "react-router-dom";

// import Navigation from ".components/Navigation";
import FieldAgentList from "./components/FieldAgentList";
import FieldAgentForm from "./components/FieldAgentForm";
import NotFound from "./components/NotFound";

function App () {
  return(
    <Router>
        <h1>Field Agents</h1>
        {/* <Navigation /> */}
        <Switch>
          <Route path="/" exact>
            <FieldAgentList />
          </Route>
          <Route path={["/agents/add", "/agents/edit/:agentId"]}>
            <FieldAgentForm />
          </Route>
          <Route path="*">
            <NotFound />
          </Route>
        </Switch>
    </Router>
  );
}
export default App;
