import { browser, element, by } from 'protractor';

import NavBarPage from './../../page-objects/navbar-page';
import SignInPage from './../../page-objects/signin-page';
import VoteComponentsPage from './vote.page-object';
import VoteUpdatePage from './vote-update.page-object';
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

describe('Vote e2e test', () => {
  let navBarPage: NavBarPage;
  let signInPage: SignInPage;
  let voteComponentsPage: VoteComponentsPage;
  let voteUpdatePage: VoteUpdatePage;

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
    voteComponentsPage = new VoteComponentsPage();
    voteComponentsPage = await voteComponentsPage.goToPage(navBarPage);
  });

  it('should load Votes', async () => {
    expect(await voteComponentsPage.title.getText()).to.match(/Votes/);
    expect(await voteComponentsPage.createButton.isEnabled()).to.be.true;
  });

  it('should create and delete Votes', async () => {
    const beforeRecordsCount = (await isVisible(voteComponentsPage.noRecords)) ? 0 : await getRecordsCount(voteComponentsPage.table);
    voteUpdatePage = await voteComponentsPage.goToCreateVote();
    await voteUpdatePage.enterData();

    expect(await voteComponentsPage.createButton.isEnabled()).to.be.true;
    await waitUntilDisplayed(voteComponentsPage.table);
    await waitUntilCount(voteComponentsPage.records, beforeRecordsCount + 1);
    expect(await voteComponentsPage.records.count()).to.eq(beforeRecordsCount + 1);

    await voteComponentsPage.deleteVote();
    if (beforeRecordsCount !== 0) {
      await waitUntilCount(voteComponentsPage.records, beforeRecordsCount);
      expect(await voteComponentsPage.records.count()).to.eq(beforeRecordsCount);
    } else {
      await waitUntilDisplayed(voteComponentsPage.noRecords);
    }
  });

  after(async () => {
    await navBarPage.autoSignOut();
  });
});
