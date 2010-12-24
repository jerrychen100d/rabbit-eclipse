/*
 * Copyright 2010 The Rabbit Eclipse Plug-in Project
 * 
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License. You may obtain a copy of
 * the License at
 * 
 * http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations under
 * the License.
 */
package rabbit.ui.internal.decorators;

import static com.google.common.collect.Lists.newArrayList;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.doAnswer;
import static org.mockito.Mockito.mock;

import org.eclipse.jface.viewers.IDecoration;
import org.joda.time.LocalDate;
import org.junit.Before;
import org.junit.Test;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;

import java.util.List;

/**
 * @see DateDecorator
 */
public class DateDecoratorTest {
  
  DateDecorator decorator;
  
  @Before
  public void create() {
    decorator = new DateDecorator();
  }

  @Test
  public void shouldAddSuffixToToday() {
    LocalDate today = new LocalDate();
    
    final List<String> args = newArrayList();
    IDecoration decoration = mock(IDecoration.class);
    doAnswer(new Answer<Void>() {
      @Override
      public Void answer(InvocationOnMock invocation) throws Throwable {
        for (Object arg : invocation.getArguments()) {
          args.add(arg.toString());
        }
        return null;
      }
    }).when(decoration).addSuffix(anyString());
    
    decorator.decorate(today, decoration);
    assertThat(args.size(), is(1));
    assertThat(args.get(0), equalTo(" [Today]"));
  }
  
  @Test
  public void shouldAddSuffixToYesterday() {
    LocalDate yesterday = new LocalDate().minusDays(1);
    
    final List<String> args = newArrayList();
    IDecoration decoration = mock(IDecoration.class);
    doAnswer(new Answer<Void>() {
      @Override
      public Void answer(InvocationOnMock invocation) throws Throwable {
        for (Object arg : invocation.getArguments()) {
          args.add(arg.toString());
        }
        return null;
      }
    }).when(decoration).addSuffix(anyString());
    
    decorator.decorate(yesterday, decoration);
    assertThat(args.size(), is(1));
    assertThat(args.get(0), equalTo(" [Yesterday]"));
  }
}
