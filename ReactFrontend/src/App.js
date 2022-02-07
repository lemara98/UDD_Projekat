import './App.css';
import SearchPage from './components/SearchPage';
import UploadPage from './components/UploadPage';
import Home from './components/Home';
import { CSSTransition, TransitionGroup } from 'react-transition-group';
import MainBar from './components/MainBar';
import { BrowserRouter as Router, Switch, Route } from 'react-router-dom'


function App() {

  return (
    <div className="App">
      <Router>
        <MainBar/>
        <Route render={({location}) => (
          <TransitionGroup>
            <CSSTransition
              key={location.pathname}
              timeout={450}
              classNames="fade">
              <Switch location={location}>
                <Route path='/' exact>
                  <Home/>
                </Route>
                <Route path='/upload'>
                  <UploadPage/>
                </Route>
                <Route path='/search'>
                  <SearchPage/>
                </Route>
              </Switch>
            </CSSTransition>
          </TransitionGroup>
        )}/>
      </Router>
    </div>
  );
}

export default App;
