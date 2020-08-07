import { browser, element, by } from 'protractor';

import NavBarPage from './../../page-objects/navbar-page';
import SignInPage from './../../page-objects/signin-page';
import MovieInsightsPerPersonComponentsPage from './movie-insights-per-person.page-object';
import MovieInsightsPerPersonUpdatePage from './movie-insights-per-person-update.page-object';
import {
  waitUntilDisplayed,
  waitUntilAnyDisplayed,
  click,
  getRecordsCount,
  waitUntilHidden,
  waitUntilCount,
  isVisible,
} from '../../util/utils';

const expect = chai.expect;

describe('MovieInsightsPerPerson e2e test', () => {
  let navBarPage: NavBarPage;
  let signInPage: SignInPage;
  let movieInsightsPerPersonComponentsPage: MovieInsightsPerPersonComponentsPage;
  let movieInsightsPerPersonUpdatePage: MovieInsightsPerPersonUpdatePage;

  before(async () => {
    await browser.get('/');
    navBarPage = new NavBarPage();
    signInPage = await navBarPage.getSignInPage();
    await signInPage.waitUntilDisplayed();

    await signInPage.username.sendKeys('admin');
    await signInPage.password.sendKeys('admin');
    await signInPage.loginButton.click();
    await signInPage.waitUntilHidden();
    await waitUntilDisplayed(navBarPage.entityMenu);
    await waitUntilDisplayed(navBarPage.adminMenu);
    await waitUntilDisplayed(navBarPage.accountMenu);
  });

  beforeEach(async () => {
    await browser.get('/');
    await waitUntilDisplayed(navBarPage.entityMenu);
    movieInsightsPerPersonComponentsPage = new MovieInsightsPerPersonComponentsPage();
    movieInsightsPerPersonComponentsPage = await movieInsightsPerPersonComponentsPage.goToPage(navBarPage);
  });

  it('should load MovieInsightsPerPeople', async () => {
    expect(await movieInsightsPerPersonComponentsPage.title.getText()).to.match(/Movie Insights Per People/);
    expect(await movieInsightsPerPersonComponentsPage.createButton.isEnabled()).to.be.true;
  });

  /* it('should create and delete MovieInsightsPerPeople', async () => {
        const beforeRecordsCount = await isVisible(movieInsightsPerPersonComponentsPage.noRecords) ? 0 : await getRecordsCount(movieInsightsPerPersonComponentsPage.table);
        movieInsightsPerPersonUpdatePage = await movieInsightsPerPersonComponentsPage.goToCreateMovieInsightsPerPerson();
        await movieInsightsPerPersonUpdatePage.enterData();

        expect(await movieInsightsPerPersonComponentsPage.createButton.isEnabled()).to.be.true;
        await waitUntilDisplayed(movieInsightsPerPersonComponentsPage.table);
        await waitUntilCount(movieInsightsPerPersonComponentsPage.records, beforeRecordsCount + 1);
        expect(await movieInsightsPerPersonComponentsPage.records.count()).to.eq(beforeRecordsCount + 1);
        
        await movieInsightsPerPersonComponentsPage.deleteMovieInsightsPerPerson();
        if(beforeRecordsCount !== 0) { 
          await waitUntilCount(movieInsightsPerPersonComponentsPage.records, beforeRecordsCount);
          expect(await movieInsightsPerPersonComponentsPage.records.count()).to.eq(beforeRecordsCount);
        } else {
          await waitUntilDisplayed(movieInsightsPerPersonComponentsPage.noRecords);
        }
    }); */

  after(async () => {
    await navBarPage.autoSignOut();
  });
});
