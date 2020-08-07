import { browser, element, by } from 'protractor';

import NavBarPage from './../../page-objects/navbar-page';
import SignInPage from './../../page-objects/signin-page';
import ImageComponentsPage from './image.page-object';
import ImageUpdatePage from './image-update.page-object';
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

describe('Image e2e test', () => {
  let navBarPage: NavBarPage;
  let signInPage: SignInPage;
  let imageComponentsPage: ImageComponentsPage;
  let imageUpdatePage: ImageUpdatePage;

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
    imageComponentsPage = new ImageComponentsPage();
    imageComponentsPage = await imageComponentsPage.goToPage(navBarPage);
  });

  it('should load Images', async () => {
    expect(await imageComponentsPage.title.getText()).to.match(/Images/);
    expect(await imageComponentsPage.createButton.isEnabled()).to.be.true;
  });

  it('should create and delete Images', async () => {
    const beforeRecordsCount = (await isVisible(imageComponentsPage.noRecords)) ? 0 : await getRecordsCount(imageComponentsPage.table);
    imageUpdatePage = await imageComponentsPage.goToCreateImage();
    await imageUpdatePage.enterData();

    expect(await imageComponentsPage.createButton.isEnabled()).to.be.true;
    await waitUntilDisplayed(imageComponentsPage.table);
    await waitUntilCount(imageComponentsPage.records, beforeRecordsCount + 1);
    expect(await imageComponentsPage.records.count()).to.eq(beforeRecordsCount + 1);

    await imageComponentsPage.deleteImage();
    if (beforeRecordsCount !== 0) {
      await waitUntilCount(imageComponentsPage.records, beforeRecordsCount);
      expect(await imageComponentsPage.records.count()).to.eq(beforeRecordsCount);
    } else {
      await waitUntilDisplayed(imageComponentsPage.noRecords);
    }
  });

  after(async () => {
    await navBarPage.autoSignOut();
  });
});
