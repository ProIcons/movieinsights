import { browser, element, by } from 'protractor';

import NavBarPage from './../../page-objects/navbar-page';
import SignInPage from './../../page-objects/signin-page';
import BannedEntityComponentsPage from './banned-entity.page-object';
import BannedEntityUpdatePage from './banned-entity-update.page-object';
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

describe('BannedEntity e2e test', () => {
  let navBarPage: NavBarPage;
  let signInPage: SignInPage;
  let bannedEntityComponentsPage: BannedEntityComponentsPage;
  let bannedEntityUpdatePage: BannedEntityUpdatePage;

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
    bannedEntityComponentsPage = new BannedEntityComponentsPage();
    bannedEntityComponentsPage = await bannedEntityComponentsPage.goToPage(navBarPage);
  });

  it('should load BannedEntities', async () => {
    expect(await bannedEntityComponentsPage.title.getText()).to.match(/Banned Entities/);
    expect(await bannedEntityComponentsPage.createButton.isEnabled()).to.be.true;
  });

  it('should create and delete BannedEntities', async () => {
    const beforeRecordsCount = (await isVisible(bannedEntityComponentsPage.noRecords))
      ? 0
      : await getRecordsCount(bannedEntityComponentsPage.table);
    bannedEntityUpdatePage = await bannedEntityComponentsPage.goToCreateBannedEntity();
    await bannedEntityUpdatePage.enterData();

    expect(await bannedEntityComponentsPage.createButton.isEnabled()).to.be.true;
    await waitUntilDisplayed(bannedEntityComponentsPage.table);
    await waitUntilCount(bannedEntityComponentsPage.records, beforeRecordsCount + 1);
    expect(await bannedEntityComponentsPage.records.count()).to.eq(beforeRecordsCount + 1);

    await bannedEntityComponentsPage.deleteBannedEntity();
    if (beforeRecordsCount !== 0) {
      await waitUntilCount(bannedEntityComponentsPage.records, beforeRecordsCount);
      expect(await bannedEntityComponentsPage.records.count()).to.eq(beforeRecordsCount);
    } else {
      await waitUntilDisplayed(bannedEntityComponentsPage.noRecords);
    }
  });

  after(async () => {
    await navBarPage.autoSignOut();
  });
});
