import { browser, element, by } from 'protractor';

import NavBarPage from './../../page-objects/navbar-page';
import SignInPage from './../../page-objects/signin-page';
import MovieComponentsPage from './movie.page-object';
import MovieUpdatePage from './movie-update.page-object';
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

describe('Movie e2e test', () => {
  let navBarPage: NavBarPage;
  let signInPage: SignInPage;
  let movieComponentsPage: MovieComponentsPage;
  let movieUpdatePage: MovieUpdatePage;

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
    movieComponentsPage = new MovieComponentsPage();
    movieComponentsPage = await movieComponentsPage.goToPage(navBarPage);
  });

  it('should load Movies', async () => {
    expect(await movieComponentsPage.title.getText()).to.match(/Movies/);
    expect(await movieComponentsPage.createButton.isEnabled()).to.be.true;
  });

  it('should create and delete Movies', async () => {
    const beforeRecordsCount = (await isVisible(movieComponentsPage.noRecords)) ? 0 : await getRecordsCount(movieComponentsPage.table);
    movieUpdatePage = await movieComponentsPage.goToCreateMovie();
    await movieUpdatePage.enterData();

    expect(await movieComponentsPage.createButton.isEnabled()).to.be.true;
    await waitUntilDisplayed(movieComponentsPage.table);
    await waitUntilCount(movieComponentsPage.records, beforeRecordsCount + 1);
    expect(await movieComponentsPage.records.count()).to.eq(beforeRecordsCount + 1);

    await movieComponentsPage.deleteMovie();
    if (beforeRecordsCount !== 0) {
      await waitUntilCount(movieComponentsPage.records, beforeRecordsCount);
      expect(await movieComponentsPage.records.count()).to.eq(beforeRecordsCount);
    } else {
      await waitUntilDisplayed(movieComponentsPage.noRecords);
    }
  });

  after(async () => {
    await navBarPage.autoSignOut();
  });
});
